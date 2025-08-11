@file:Suppress("DEPRECATION")

package com.emirhan.matko.ui.pages

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.emirhan.matko.R
import com.emirhan.matko.ui.theme.MatkoFontFamily
import com.emirhan.matko.ui.theme.MatkoTheme
import com.emirhan.matko.ui.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInSelectionScreen(modifier: Modifier,navController: NavController,viewModel: AuthViewModel){
    Column(modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        TypewriterText("GİRİŞ YAP...")

        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        var showSheet by remember { mutableStateOf(true) } // Açık başlasın diye true
        val context=LocalContext.current

        val gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(stringResource(R.string.web_client_id))
            .requestEmail()
            .build()
        val googleSignClient= GoogleSignIn.getClient(context,gso)

        val authResultLauncher= rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = {result ->
                val task= GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account=task.getResult(ApiException::class.java)
                    account.idToken?.let { idToken->
                        viewModel.signInWithGoogle(idToken){isNewUSer ->
                            if (isNewUSer){
                                navController.navigate("examSelection_screen"){
                                    popUpTo("signInSelection_screen"){inclusive=true}
                                }
                            }else{
                                navController.navigate("chat_screen"){
                                    popUpTo("signInSelection_screen"){inclusive=true}
                                }
                            }
                        }
                    }
                }catch (e: ApiException){
                    Toast.makeText(context,"Google giriş hatası: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        )

        // Sayfa ilk açıldığında sheet gösterilecek
        LaunchedEffect(Unit) {
            sheetState.show()
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Column(modifier = Modifier
                    .padding(16.dp)
                    .height(100.dp)
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Button(onClick = {
                        val signInIntent=googleSignClient.signInIntent
                        authResultLauncher.launch(signInIntent)
                        showSheet = false
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = modifier
                            .fillMaxWidth(0.8f),
                        border =BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary)) {
                        Icon(painter = painterResource(R.drawable.google_icon),contentDescription = null,modifier.size(24.dp), tint = MaterialTheme.colorScheme.tertiary)
                        Spacer(modifier.padding(horizontal = 5.dp))
                        Text("GOOGLE İLE GİRİŞ YAP",
                            color = MaterialTheme.colorScheme.tertiary,
                            fontFamily = MatkoFontFamily,
                            fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        showSheet = false
                        navController.navigate("login_screen")
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = modifier
                            .fillMaxWidth(0.8f),
                        border = BorderStroke(3.dp, color = MaterialTheme.colorScheme.primary)) {
                        Text("E-MAİL & ŞİFRE İLE GİRİŞ YAP",
                            color = MaterialTheme.colorScheme.tertiary,
                            fontFamily = MatkoFontFamily,
                            fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignInSelectionPreview(){
    MatkoTheme(dynamicColor = false) {
        //SignInSelectionScreen(modifier = Modifier, navController = NavController(context = LocalContext.current))
    }
}