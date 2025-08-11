package com.emirhan.matko.ui.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.emirhan.matko.R
import com.emirhan.matko.ui.model.UserModel
import com.emirhan.matko.ui.theme.MatkoFontFamily
import com.emirhan.matko.ui.theme.MatkoTheme
import com.emirhan.matko.ui.util.LoginState
import com.emirhan.matko.ui.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun LoginScreen(modifier: Modifier,navController: NavController,viewModel: AuthViewModel){
    val state=viewModel.loginState.value
    val auth= FirebaseAuth.getInstance()
    val db= FirebaseFirestore.getInstance()
    val screenWith= LocalConfiguration.current.screenWidthDp.dp
    val screenHeight=LocalConfiguration.current.screenHeightDp.dp
    var eMail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val scrollState= rememberScrollState()
    var userState by remember { mutableStateOf<UserModel?>(null) }

    val snackbarHostState=remember { SnackbarHostState() }
    val scope= rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val curretUser=auth.currentUser
        curretUser?.uid?.let {
            uid->
            db.collection("users").document(uid)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()){
                        userState= UserModel(
                            uid = snapshot.getString("uid")?:"",
                            name = snapshot.getString("name")?:""
                        )
                    }
                }
        }
    }


    LaunchedEffect(state) {
        when(state){
            is LoginState.Success -> {
                navController.navigate("chat_screen") {
                    popUpTo("login_screen") { inclusive = true }
                }
            }
            is LoginState.Error -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Hata: ${state.message}",
                        duration = SnackbarDuration.Short
                    )
                }
            }
            else -> {}
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)) {
        TypewriterText("HAYDİ GİRİŞ YAPALIM")
        Spacer(modifier.padding(vertical = 10.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            ),
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            modifier = modifier.height(screenHeight*0.4f)
                .width(screenWith*0.9f)
        ) {
            Column(modifier.fillMaxSize().verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    modifier = modifier.padding(all = 10.dp),
                    value = eMail,
                    onValueChange = {eMail=it},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Email,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary)
                    },
                    placeholder = {Text("E Mail:",
                        fontFamily = MatkoFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary)},
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                        cursorColor = MaterialTheme.colorScheme.tertiary
                    ),
                    textStyle = TextStyle(
                        fontFamily = MatkoFontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                OutlinedTextField(
                    modifier = modifier.padding(all = 10.dp),
                    value = password,
                    onValueChange = {password=it},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    leadingIcon = {Icon(
                        imageVector = Icons.Filled.Password,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )},
                    trailingIcon = {
                        IconButton(onClick = {passwordVisibility=!passwordVisibility},
                            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.secondary)) {
                            val image=if (passwordVisibility){
                                painterResource(R.drawable.baseline_visibility_24)
                            }else{
                                painterResource(R.drawable.outline_visibility_off_24)
                            }
                            Icon(painter = image,contentDescription = null)
                        }
                    },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    placeholder = {Text("Şifre:",
                        fontFamily = MatkoFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary)},
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                        cursorColor = MaterialTheme.colorScheme.tertiary
                    ),
                    textStyle = TextStyle(
                        fontFamily = MatkoFontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(all = 5.dp)) {
                    Text(
                        text = "Hesabın yok mu?",
                        fontFamily = MatkoFontFamily,
                        fontWeight = FontWeight.Thin,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    Spacer(modifier.padding(horizontal = 5.dp))
                    Text(
                        text = "Kayıt ol.",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = MatkoFontFamily,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 15.sp,
                        modifier=modifier.clickable{
                            navController.navigate("register_screen")
                        }
                    )
                }
                OutlinedButton(onClick = {
                    if (eMail.isNotEmpty()&&password.isNotEmpty()){
                        viewModel.loginUser(eMail.trim(),password.trim())
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Giriş yapılıyor ${userState?.name}",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }else{
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Lütfen istenen bilgileri eksiksiz doldurun",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                },
                    colors = ButtonDefaults
                        .outlinedButtonColors(containerColor = Color.White,
                            contentColor = MaterialTheme.colorScheme.secondary),
                    border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary),
                    enabled = state != LoginState.Loading
                ) {
                    Text("GİRİŞ", fontFamily = MatkoFontFamily, fontWeight = FontWeight.SemiBold)
                }
                when(state){
                    is LoginState.Loading -> CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary, strokeWidth = 3.dp)
                    is LoginState.Error -> Text("Hata: ${state.message}", color = Color.Red)
                    else -> {}
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPreview(){
    MatkoTheme(dynamicColor = false) {
        //LoginScreen(modifier = Modifier, navController = NavController(context = LocalContext.current))
    }
}