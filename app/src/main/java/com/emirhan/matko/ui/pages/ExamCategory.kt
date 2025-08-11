package com.emirhan.matko.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.emirhan.matko.ui.desing.ButtonDesing
import com.emirhan.matko.ui.theme.MatkoTheme
import com.emirhan.matko.ui.viewmodel.AuthViewModel

@Composable
fun ExamCategoryScreen(modifier: Modifier,navController: NavController,viewModel: AuthViewModel){

    Scaffold(content = { innerPadding->
        Column(modifier
            .padding(innerPadding)
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            TypewriterText("Demek KPSS \n \n  çalışıyoruz peki \n \n hangisine?")
            Spacer(modifier.padding(all = 30.dp))
            ButtonDesing(
                onClick = {viewModel.updateSelectedCategory("OrtaOğretim")
                navController.navigate("levelSelection_screen")},
                examCategory = "OrtaÖğretim"
            )
            ButtonDesing(
                onClick = {
                    viewModel.updateSelectedCategory("Ön Lisans")
                    navController.navigate("levelSelection_screen")
                },
                examCategory = "Ön Lisans"
            )
            ButtonDesing(
                onClick = {
                    viewModel.updateSelectedCategory("Lisans")
                    navController.navigate("levelSelection_screen")
                },
                examCategory = "Lisans"
            )
        }
    },
        floatingActionButton = {
            FloatingActionButton(onClick = {navController.popBackStack()}, containerColor = MaterialTheme.colorScheme.secondary, contentColor = MaterialTheme.colorScheme.primary) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.Center)

}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExamCategoryPreview(){
    MatkoTheme(dynamicColor = false) {
        //ExamCategoryScreen(modifier = Modifier, navController = NavController(context = LocalContext.current))
    }
}