package com.emirhan.matko.ui.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.emirhan.matko.ui.theme.MatkoFontFamily
import com.emirhan.matko.ui.theme.MatkoTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(modifier: Modifier,navController: NavController){
    Column(modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
            TypewriterText(text = "MATKO'YA")
            TypewriterText("HOŞGELDİNİZ")
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        var showSheet by remember { mutableStateOf(true) } // Açık başlasın diye true

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
                        showSheet = false
                        navController.navigate("signInSelection_screen")

                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = modifier
                            .fillMaxWidth(0.8f),
                        border =BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary)) {
                        Text("GİRİŞ YAP",
                            color = MaterialTheme.colorScheme.tertiary,
                            fontFamily = MatkoFontFamily,
                            fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        showSheet = false
                        navController.navigate("signUpSelection_screen")
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = modifier
                            .fillMaxWidth(0.8f),
                        border = BorderStroke(3.dp, color = MaterialTheme.colorScheme.primary)) {
                        Text("KAYIT OL",
                            color = MaterialTheme.colorScheme.tertiary,
                            fontFamily = MatkoFontFamily,
                            fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

//typewriter effect
@Composable
fun TypewriterText(
    text: String,
    delayBetweenCharacters: Long = 100L
) {
    var displayedText by remember { mutableStateOf("") }

    // Composable ilk yüklendiğinde çalışacak.
    LaunchedEffect(key1 = text) {
        text.forEachIndexed { index, _ ->
            // Her karakteri tek tek ekle
            displayedText = text.substring(0, index + 1)
            // Belirlenen süre kadar bekle
            delay(delayBetweenCharacters)
        }
    }

    Text(
        text = displayedText,
        fontSize = 40.sp,
        color = MaterialTheme.colorScheme.tertiary,
        fontFamily = MatkoFontFamily,
        fontWeight = FontWeight.ExtraBold
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomePreview(){
    MatkoTheme(dynamicColor = false) {
        WelcomeScreen(modifier = Modifier, navController = NavHostController(context = LocalContext.current))
    }
}