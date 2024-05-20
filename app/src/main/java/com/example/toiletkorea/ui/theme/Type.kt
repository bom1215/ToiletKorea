package com.example.toiletkorea.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.toiletkorea.R


val AbrilFatface = FontFamily(
    Font(R.font.abrilfatface_regular)
)

val Montserrat = FontFamily(
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_regular)
)
// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = AbrilFatface,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Light,
        fontSize = 10.sp,
        lineHeight = 18.sp
    )

)

//displayLarge	Roboto 57/64
//displayMedium	Roboto 45/52
//displaySmall	Roboto 36/44
//headlineLarge	Roboto 32/40
//headlineMedium	Roboto 28/36
//headlineSmall	Roboto 24/32
//titleLarge	New- Roboto Medium 22/28
//titleMedium	Roboto Medium 16/24
//titleSmall	Roboto Medium 14/20
//bodyLarge	Roboto 16/24
//bodyMedium	Roboto 14/20
//bodySmall	Roboto 12/16
//labelLarge	Roboto Medium 14/20
//labelMedium	Roboto Medium 12/16
//labelSmall	New Roboto Medium, 11/16


    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
