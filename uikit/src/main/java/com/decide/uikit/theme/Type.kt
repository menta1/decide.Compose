package com.decide.uikit.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.decide.uikit.R

private val FiraSans = FontFamily(
    Font(R.font.fira_sans_light),
    Font(R.font.fira_sans_regular),
    Font(R.font.fira_sans_semi_bold),
    Font(R.font.fira_sans_bold)
)

private val Mulish = FontFamily(
    Font(R.font.mulish_bold),
    Font(R.font.mulish_extra_bold),
    Font(R.font.mulish_regular)
)

private val Roboto = FontFamily(
    Font(R.font.roboto_black),
    Font(R.font.roboto_bold),
    Font(R.font.roboto_medium),
    Font(R.font.roboto_regular),
    Font(R.font.roboto_light),
    Font(R.font.roboto_thin),
)

private val Nunito = FontFamily(
    Font(R.font.nunito_sans_bold),
    Font(R.font.nunito_sans_black),
    Font(R.font.nunito_sans_light),
    Font(R.font.nunito_sans_regular),
    Font(R.font.nunito_sans_medium),
    Font(R.font.nunito_sans_extra_bold),
    Font(R.font.nunito_sans_extra_light),
    Font(R.font.nunito_sans_semi_bold),
)

val DecideTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Black,
        fontSize = 68.sp,
        lineHeight = 64.sp,
        letterSpacing = 3.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.8.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(//+
        fontFamily = Nunito,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.3.sp
    ),
    headlineMedium = TextStyle(//+
        fontFamily = Nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        letterSpacing = 0.3.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(//
        fontFamily = Nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(//+
        fontFamily = Nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 23.sp,
        lineHeight = 23.sp,
        letterSpacing = 0.3.sp
    ),
    titleSmall = TextStyle(//+
        fontFamily = Nunito,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = (-0.2).sp
    ),
    bodyLarge = TextStyle(//+
        fontFamily = Nunito,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(//+
        fontFamily = Nunito,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.15.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(//cardLarge
        fontFamily = Nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(//tabBar
        fontFamily = Nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(//+
        fontFamily = Nunito,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 13.sp,
        letterSpacing = 0.sp
    )
)