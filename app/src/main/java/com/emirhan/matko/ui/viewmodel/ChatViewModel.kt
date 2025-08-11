package com.emirhan.matko.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhan.matko.ui.model.ChatMessage
import com.emirhan.matko.ui.model.OpenRouterRequest
import com.emirhan.matko.ui.util.OpenRouterApiService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ChatViewModel  @Inject constructor(
    private val auth: FirebaseAuth,
    private val db:FirebaseFirestore,
    private val apiService: OpenRouterApiService,
    @Named("OpenRouterApiKey") private val apiKey: String
): ViewModel(){

    val messages= mutableStateListOf<ChatMessage>()

    var selectedExam=mutableStateOf("")
    private var selectedCategory: String=""
    private var mathLevel: String=""

    init {
        loadUserPreferences()
    }

    private fun loadUserPreferences(){
        val currentUser=auth.currentUser
        currentUser?.uid?.let { uid->
            db.collection("users").document(uid).get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()){
                        selectedExam.value=documentSnapshot.getString("selectedExam")?:""
                        selectedCategory=documentSnapshot.getString("examSubCategory")?:""
                        mathLevel=documentSnapshot.getString("mathLevel")?:"Temel"

                        val systemPrompt=createSystemPrompt()
                        messages.add(ChatMessage(role = "system", content = systemPrompt))
                    }
                }
                .addOnFailureListener { exception ->
                    messages.add(ChatMessage(role = "system", content = "Sen bir matematik koçusun soruları cevapla"))
                }
        }
    }
    private fun createSystemPrompt(): String{
        val categoryText=if (selectedCategory.isNotEmpty()) "ve $selectedCategory alanına" else ""
        return "Sen Matko adında bir matematik koçusun. " +
                "Öğrenci $selectedExam sınavına hazırlanıyor, matematik seviyesi $mathLevel.$categoryText " +
                "Yalnızca matematik ile ilgili soruları, seviyesine uygun ve bir matematik öğretmeni gibi anlatarak çöz. Kullanıcı senden çalışma programı isterse ona hangi matematik konularından program yapması gerektiğini sor ve profesyonel şekilde program yaz." +
                "Cevapları uzatma isteneni yap. Kesinlikle markdown (**,#,- gibi) işaretleri kullanma cevaplarını sade tut."+
                "Kullanıcı konu dışı soru sorarsa kibarca tekrar matematiğe yönlendir."+
                "Yazım hataları yapma cevaplarını uzatma öğretmeye yönelik ve gerektiği kadar uzunlukta cevap yaz. Sen kimsin kendini tanıt gibi sorularda Merhaba ben Matko diye cümleye başla."+
                "Soru çözümlerinde matematiksel işaretlemeleri düzgün kullan profesyonel şekilde cevaplar hazırla."
    }
    fun sendMessage(userMessage: String){
        messages.add(ChatMessage(role = "user", content = userMessage))
        viewModelScope.launch {
            try {
                val messagesToSend=messages.toList()
                val request= OpenRouterRequest(
                    model = "deepseek/deepseek-r1-0528:free",
                    messages = messagesToSend
                )
                val response=apiService.getChatRepsonse("Bearer $apiKey",request)
                val assistantMessage=response.choices.firstOrNull()?.message?.content

                assistantMessage?.let {
                    val cleanedMessage=it.replace("**","")
                    messages.add(ChatMessage(role = "assistant", content = cleanedMessage))
                }?:run {
                    messages.add(ChatMessage(role = "assistant", content = "Yanıt alınamadı."))
                }
            }catch (e: Exception){
                e.printStackTrace()
                messages.add(ChatMessage(role = "assistant", content = "Bir hata oluştu ${e.message}"))
            }
        }
    }
}