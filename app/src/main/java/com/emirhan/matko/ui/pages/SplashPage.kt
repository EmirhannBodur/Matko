package com.emirhan.matko.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.emirhan.matko.ui.theme.MatkoFontFamily
import com.emirhan.matko.ui.theme.MatkoTheme
import com.emirhan.matko.ui.theme.lightThemeAppTitleColor
import com.emirhan.matko.ui.theme.lightThemeFirstColor
import com.emirhan.matko.ui.viewmodel.AuthViewModel
import com.emirhan.matko.ui.viewmodel.UserStatus

@Composable
fun SplashScreen(modifier: Modifier,navController: NavController,viewModel: AuthViewModel){
    LaunchedEffect(Unit) {
        viewModel.checkUserStatus { userStatus ->
            val route=when(userStatus){
                UserStatus.AUTHENTICATED_AND_ONBOARDED -> "chat_screen"
                UserStatus.NEEDS_ONBOARDING -> "examSelection_screen"
                UserStatus.NOT_AUTHENTICATED -> "welcome_screen"
            }
            navController.navigate(route){
                popUpTo("splash_screen"){inclusive=true}
            }
        }
    }
    Column(modifier
        .fillMaxSize()
        .background(lightThemeFirstColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(
            text = "MATKO",
            fontFamily = MatkoFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 80.sp,
            color = lightThemeAppTitleColor
        )
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashPreview(){
    MatkoTheme {
        //SplashScreen(modifier = Modifier, navController = NavHostController(context = LocalContext.current))
    }
}