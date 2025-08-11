package com.emirhan.matko.ui.theme


import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.emirhan.matko.R

private val weight = FontWeight

val MatkoFontFamily= FontFamily(
     Font(R.font.rubik_black, weight = weight.Black),
    Font(R.font.rubik_bold, weight = weight.Bold),
    Font(R.font.rubik_light,weight.Light),
    Font(R.font.rubik_italic,weight.Normal),
    Font(R.font.rubik_medium,weight.Medium),
    Font(R.font.rubik_blackitalic,weight.Black),
    Font(R.font.rubik_bolditalic,weight.Bold),
    Font(R.font.rubik_extrabold,weight.ExtraBold),
    Font(R.font.rubik_extrabolditalic,weight.ExtraBold),
    Font(R.font.rubik_lightitalic,weight.Light),
    Font(R.font.rubik_mediumitalic,weight.Medium),
    Font(R.font.rubik_regular,weight.Normal),
    Font(R.font.rubik_semibold,weight.SemiBold),
    Font(R.font.rubik_semibolditalic,weight.SemiBold)
)