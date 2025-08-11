package com.emirhan.matko.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.emirhan.matko.ui.model.UserModel
import com.emirhan.matko.ui.util.LoginState
import com.emirhan.matko.ui.util.RegisterState
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


// Kullanıcı durumunu belirtmek için yeni bir enum class
enum class UserStatus {
    NOT_AUTHENTICATED,
    NEEDS_ONBOARDING,
    AUTHENTICATED_AND_ONBOARDED
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db:FirebaseFirestore
): ViewModel() {


    private val _selectedExam = mutableStateOf("")
    private val _selectedCategory = mutableStateOf("")
    private val _mathLevel = mutableStateOf("")

    var registerState= mutableStateOf<RegisterState>(RegisterState.Idle)
        private set
    fun registerUser(email: String,password: String,name: String,onSuccess: () -> Unit={},onError: (String) -> Unit={}){
        registerState.value= RegisterState.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener { result ->
                val uid=result.user?.uid?:return@addOnSuccessListener
                saveUser(uid,name,email){isSuccess->
                    if (isSuccess) onSuccess()
                    else onError("Veri kadedilmedi")
                }
            }
            .addOnFailureListener {
                registerState.value= RegisterState.Error("Kayıt hatası: ${it.message}")
                onError("Kayıt hatası: ${it.message}")
            }
    }
    private fun saveUser(uid: String,name: String,email: String,onComplete:(Boolean)-> Unit?={_ ->}){
        val userData = UserModel(
            uid =  uid,
            name =  name,
            email =  email,
            timestamp = Timestamp.now().seconds,
            selectedExam = "",
            examSubCategory = "",
            mathLevel = ""
        )

        db.collection("users").document(uid).set(userData)
            .addOnSuccessListener {
                registerState.value = RegisterState.Success
                onComplete(true)
            }
            .addOnFailureListener {
                registerState.value = RegisterState.Error("Veri Firestore'a kaydedilemedi: ${it.message}")
                onComplete(false)
            }
    }
    var loginState=mutableStateOf<LoginState>(LoginState.Idle)
        private set
    fun loginUser(email: String,password: String){
        loginState.value= LoginState.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                loginState.value= LoginState.Success
            }
            .addOnFailureListener { exception ->
                loginState.value= LoginState.Error("Giriş Hatası ${exception.message}")
            }
    }
    fun signInWithGoogle(idToken: String,onResult:(isNewUSer: Boolean)-> Unit){
        loginState.value= LoginState.Loading
        val credential= GoogleAuthProvider.getCredential(idToken,null)

        auth.signInWithCredential(credential)
            .addOnSuccessListener { result ->
                val user=result.user
                if (user!=null){
                    db.collection("users").document(user.uid).get()
                        .addOnSuccessListener { documentSnapshot ->
                            val isNewUser= !documentSnapshot.exists()
                            if (isNewUser){
                                //Yeni kullanıcı, Firestore'a kaydet
                                val name=user.displayName?:"Bilinmeyen kullanıcı"
                                val email=user.email?:""
                                saveUser(uid = user.uid, name = name, email = email){
                                    onResult(true)
                                }
                            }else{
                                loginState.value= LoginState.Success
                                onResult(false)
                            }
                        }
                        .addOnFailureListener { exception ->
                            loginState.value= LoginState.Error("FireStore kontrol hatası")
                        }
                }else{
                    loginState.value= LoginState.Error("Kullanıcı bilgisi alınamadı")
                }
            }
            .addOnFailureListener { exception ->
                    loginState.value= LoginState.Error("Hata:${exception.message}")
            }
    }
    // YENİ EKLENEN KISIM: Kullanıcının durumunu kontrol eden fonksiyon
    fun checkUserStatus(onResult: (UserStatus) -> Unit) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            onResult(UserStatus.NOT_AUTHENTICATED)
            return
        }

        db.collection("users").document(currentUser.uid).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val selectedExam = documentSnapshot.getString("selectedExam")
                    val mathLevel = documentSnapshot.getString("mathLevel")

                    // Bu alanlar yoksa veya boşsa, anket tamamlanmamış demektir.
                    if (selectedExam.isNullOrEmpty() || mathLevel.isNullOrEmpty()) {
                        onResult(UserStatus.NEEDS_ONBOARDING)
                    } else {
                        onResult(UserStatus.AUTHENTICATED_AND_ONBOARDED)
                    }
                } else {
                    // Kullanıcı Firebase'e kayıtlı ama Firestore'da yok (nadiren olur)
                    onResult(UserStatus.NOT_AUTHENTICATED)
                }
            }
            .addOnFailureListener {
                // Firestore'dan veri çekme hatası olursa
                onResult(UserStatus.NOT_AUTHENTICATED)
            }
    }
    fun updateSelectedExam(exam: String) {
        _selectedExam.value=exam
        println("Seçilen Sınav: $exam")
    }

    fun updateSelectedCategory(category: String) {
        _selectedCategory.value=category
        println("Alt kategori: $category")
    }

    fun updateMathLevel(level: String) {
        _mathLevel.value=level
        println("Seviye: $level")
    }
    fun saveUserProgressToFirestore(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val selectedExam = _selectedExam.value
        val selectedCategory = _selectedCategory.value
        val mathLevel = _mathLevel.value

        println("Firestore'a gidecek veri: {selectedExam=$selectedExam, selectedCategory=$selectedCategory, mathLevel=$mathLevel}")

        val uid = auth.currentUser?.uid
        if (uid == null) {
            onError("UID bulunamadı")
            return
        }

        val data = mapOf(
            "selectedExam" to selectedExam,
            "examSubCategory" to selectedCategory,
            "mathLevel" to mathLevel
        )

        println("Veriler geliyomu: $data")

        db.collection("users")
            .document(uid)
            .update(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e.message ?: "Bilinmeyen hata") }
    }
}