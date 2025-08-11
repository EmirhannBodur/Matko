package com.emirhan.matko

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.emirhan.matko.ui.pages.ChatScreen
import com.emirhan.matko.ui.pages.ExamCategoryScreen
import com.emirhan.matko.ui.pages.ExamSelectionScreen
import com.emirhan.matko.ui.pages.LevelSelectionScreen
import com.emirhan.matko.ui.pages.LoginScreen
import com.emirhan.matko.ui.pages.RegisterScreen
import com.emirhan.matko.ui.pages.SignInSelectionScreen
import com.emirhan.matko.ui.pages.SignUpSelectionScreen
import com.emirhan.matko.ui.pages.SplashScreen
import com.emirhan.matko.ui.pages.WelcomeScreen
import com.emirhan.matko.ui.theme.MatkoTheme
import com.emirhan.matko.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController= rememberNavController()
            val authViewModel: AuthViewModel= hiltViewModel()
            MatkoTheme(dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        NavHost(navController = navController, startDestination = "splash_screen"){
                            composable("splash_screen") { SplashScreen(modifier = Modifier, navController = navController, viewModel = authViewModel) }
                            composable("welcome_screen") { WelcomeScreen(modifier = Modifier, navController = navController) }
                            composable("signInSelection_screen") { SignInSelectionScreen(modifier = Modifier, navController = navController, viewModel = authViewModel)}
                            composable("signUpSelection_screen") { SignUpSelectionScreen(modifier = Modifier, navController = navController, viewModel = authViewModel)}
                            composable("register_screen") { RegisterScreen(modifier = Modifier, navController = navController, viewModel = authViewModel) }
                            composable("login_screen") { LoginScreen(modifier = Modifier, navController = navController, viewModel = authViewModel) }
                            composable("examSelection_screen") { ExamSelectionScreen(modifier = Modifier, navController = navController,authViewModel) }
                            composable("examCategory_screen") { ExamCategoryScreen(modifier = Modifier, navController = navController,authViewModel) }
                            composable("levelSelection_screen") { LevelSelectionScreen(modifier = Modifier, navController = navController,authViewModel) }
                            composable("chat_screen") { ChatScreen(modifier = Modifier, navController = navController, viewModel = hiltViewModel())}
                        }
                    }
                }
            }
        }
    }
}

