package com.example.fics_compose.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.fics_compose.R

val fenixFont = FontFamily(
    Font(R.font.fenix_regular)
)

val fjallafont = FontFamily(
    Font(R.font.fjallaoneregular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = fenixFont,
        fontWeight = FontWeight(400),
        fontSize = 32.sp,
        lineHeight = 62.4.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = fenixFont,
        fontWeight = FontWeight(400),
        fontSize = 52.sp,
        lineHeight = 62.4.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = fenixFont,
        fontWeight = FontWeight(400),
        fontSize = 20.sp,
        lineHeight = 25.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = fenixFont,
        fontWeight = FontWeight(400),
        fontSize = 17.sp,
        lineHeight = 20.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = fjallafont,
        fontWeight = FontWeight(400),
        fontSize = 20.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = fjallafont,
        fontWeight = FontWeight(400),
        fontSize = 27.sp,
        lineHeight = 30.sp,
    ),
    displayLarge = TextStyle(
        fontFamily = fenixFont,
        fontWeight = FontWeight(400),
        fontSize = 28.sp,
        lineHeight = 25.sp,
    ),
)

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


