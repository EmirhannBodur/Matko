package com.emirhan.matko.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.emirhan.matko.ui.desing.ExamButtonDesing
import com.emirhan.matko.ui.model.UserModel
import com.emirhan.matko.ui.theme.MatkoTheme
import com.emirhan.matko.ui.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExamSelectionScreen(modifier: Modifier,navController: NavController,viewModel: AuthViewModel) {
    val auth= FirebaseAuth.getInstance()
    val db= FirebaseFirestore.getInstance()
    var userState by remember { mutableStateOf<UserModel?>(null) }

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

    Column(
        modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier.padding(all = 20.dp))
        TypewriterText("\n \n  Merhaba ${userState?.name.toString()} \n \n  önce odaklanacağımız \n \n  sınavı seçelim...")
        Spacer(modifier.padding(all = 20.dp))
        FlowRow(
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.padding(all = 10.dp)
        ) {
            ExamButtonDesing(
                onClick = {
                    viewModel.updateSelectedExam("TYT")
                    navController.navigate("levelSelection_screen")},
                examName = "TYT"
            )
            ExamButtonDesing(
                onClick = {
                    viewModel.updateSelectedExam("AYT")
                    navController.navigate("levelSelection_screen")
                          },
                examName = "AYT"
            )
            ExamButtonDesing(
                onClick = {
                    viewModel.updateSelectedExam("ALES")
                    navController.navigate("levelSelection_screen")},
                examName = "ALES"
            )
            ExamButtonDesing(
                onClick = {
                    viewModel.updateSelectedExam("MSÜ")
                    navController.navigate("levelSelection_screen")},
                examName = "MSÜ"
            )
            ExamButtonDesing(
                onClick = {
                    viewModel.updateSelectedExam("DGS")
                    navController.navigate("levelSelection_screen")},
                examName = "DGS"
            )
            ExamButtonDesing(
                onClick = {
                    viewModel.updateSelectedExam("KPSS-A")
                    navController.navigate("levelSelection_screen")},
                examName = "KPSS-A"
            )
            ExamButtonDesing(
                onClick = {
                    viewModel.updateSelectedExam("KPSS-B")
                    navController.navigate("examCategory_screen")},
                examName = "KPSS-B"
            )
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExamselectionPreview(){
    MatkoTheme(dynamicColor = false) {
        //ExamSelectionScreen(modifier = Modifier, navController = NavController(context = LocalContext.current))
    }
}