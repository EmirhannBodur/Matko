package com.emirhan.matko.ui.desing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emirhan.matko.ui.theme.MatkoFontFamily

@Composable
fun LevelSelectionButtonDesing(
    onClick:()-> Unit,
    level: String?=null
){
    OutlinedButton(onClick, content = {
        Text(level!!, fontFamily = MatkoFontFamily, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.secondary)
    },
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
        border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.secondary),
        modifier = Modifier.padding(all = 5.dp)
            .width(150.dp))
}

@Composable
fun ExamButtonDesing(
    onClick:()-> Unit,
    examName: String?=null
){
    OutlinedButton(onClick, content = {
        Text(examName!!, fontFamily = MatkoFontFamily, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.secondary)
    },
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
        border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.secondary),
        modifier = Modifier.padding(all = 5.dp))
}

@Composable
fun ButtonDesing(
    onClick:()-> Unit,
    examCategory: String?=null
){
    OutlinedButton(onClick, content = {
        Text(examCategory!!, fontFamily = MatkoFontFamily, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.secondary)
    },
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
        border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.secondary),
        modifier = Modifier.padding(all = 5.dp)
            .width(150.dp))
}