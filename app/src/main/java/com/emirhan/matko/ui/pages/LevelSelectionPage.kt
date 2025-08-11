package com.emirhan.matko.ui.pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.emirhan.matko.ui.desing.LevelSelectionButtonDesing
import com.emirhan.matko.ui.theme.MatkoTheme
import com.emirhan.matko.ui.viewmodel.AuthViewModel

@Composable
fun LevelSelectionScreen(modifier: Modifier,navController: NavController,viewModel: AuthViewModel){
    val context=LocalContext.current
    Scaffold(content = {
        innerPadding->
        Column(modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            TypewriterText("\n \n  Evetttt son sorumuza \n \n geldik matematikle \n  \n aramız nasıl?")
            Spacer(modifier.padding(all = 30.dp))
            LevelSelectionButtonDesing(
                onClick = {
                    viewModel.updateMathLevel("Temel")
                    viewModel.saveUserProgressToFirestore(
                        onSuccess = {
                            navController.navigate("chat_screen")
                        },
                        onError = {
                            Toast.makeText(context,"Hata", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                level = "Temel"
            )
            LevelSelectionButtonDesing(
                onClick = {
                    viewModel.updateMathLevel("Orta")
                    viewModel.saveUserProgressToFirestore(
                        onSuccess = {navController.navigate("chat_screen")},
                        onError = {
                            Toast.makeText(context,"Hata", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                level = "Orta"
            )
            LevelSelectionButtonDesing(
                onClick = {
                    viewModel.updateMathLevel("İleri")
                    viewModel.saveUserProgressToFirestore(
                        onSuccess = {navController.navigate("chat_screen")},
                        onError = {
                            Toast.makeText(context,"Hata", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                level = "İleri"
            )
        }
    },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.popBackStack()
            },
                content = {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,contentDescription = null,modifier.size(30.dp))
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.primary)
        },
        floatingActionButtonPosition = FabPosition.Center
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LevelSelectionPreview(){
    MatkoTheme(dynamicColor = false) {
        //LevelSelectionScreen(modifier = Modifier, navController = NavController(context = LocalContext.current))
    }
}