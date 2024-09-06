package com.decide.uikit.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.decide.uikit.R

//Fira Sans
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

val DecideTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Mulish,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = 0.15.sp
    ),
    displayMedium = TextStyle(
        fontFamily = FiraSans,
        fontWeight = FontWeight.Light,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Mulish,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FiraSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FiraSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FiraSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(//titleScreen//buttonLarge
        fontFamily = FiraSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(//heading//cardAccent//searchText
        fontFamily = FiraSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Mulish,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(//defaultTextEmpty//body
        fontFamily = Mulish,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(//assay
        fontFamily = FiraSans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Mulish,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(//cardLarge
        fontFamily = FiraSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(//tabBar
        fontFamily = FiraSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(//tabBarSelected
        fontFamily = FiraSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)


val firaSansRegular = FontFamily(Font(R.font.fira_sans_regular))
val firaSansBold = FontFamily(Font(R.font.fira_sans_bold))
val firaSansSemiBold = FontFamily(Font(R.font.fira_sans_semi_bold))
val firaSansLight = FontFamily(Font(R.font.fira_sans_light))

//Mulish
val mulishBold = FontFamily(Font(R.font.mulish_bold))
val mulishExtraBold = FontFamily(Font(R.font.mulish_extra_bold))

//Jost
val jostSemiBold = FontFamily(Font(R.font.jost_semi_bold))

@Immutable
data class DecideeTypography(
    //Fira Sans
    val body: TextStyle = TextStyle(
        fontFamily = firaSansRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp,
    ),
    val heading: TextStyle = TextStyle(
        fontFamily = firaSansRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 20.sp,
    ),
    val headingDesc: TextStyle = TextStyle(
        fontFamily = firaSansSemiBold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 22.sp,
    ),
    val headingDesc2: TextStyle = TextStyle(
        fontFamily = firaSansSemiBold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 25.sp,
    ),
    val bodyText: TextStyle = TextStyle(
        fontFamily = firaSansLight,
        fontWeight = FontWeight.Light,
        fontSize = 17.sp,
        lineHeight = 22.sp,
    ),
    val bodyClarification: TextStyle = TextStyle(
        fontFamily = firaSansLight,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 16.sp,
    ),


    val titleAccent: TextStyle = TextStyle(
        fontFamily = firaSansBold,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.32).sp,
    ),
    val titleLarge: TextStyle = TextStyle(
        fontFamily = firaSansRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.32).sp,
    ),
    val defaultTextEmpty: TextStyle = TextStyle(
        fontFamily = firaSansRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
    ),
    val cardAccent: TextStyle = TextStyle(
        fontFamily = mulishBold,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 15.sp,
        //letterSpacing = (-0.32).sp,
    ),
    val cardLarge: TextStyle = TextStyle(
        fontFamily = firaSansLight,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 19.sp,
        //letterSpacing = (-0.32).sp,
    ),
    val buttonLarge: TextStyle = TextStyle(
        fontFamily = firaSansSemiBold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 17.sp,
        letterSpacing = (-0.32).sp,
    ),

    //Mulish
    val tabBar: TextStyle = TextStyle(
        fontFamily = mulishBold,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 12.sp,
    ),
    val tabBarSelected: TextStyle = TextStyle(
        fontFamily = mulishExtraBold,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 12.sp,
    ),
    val assay: TextStyle = TextStyle(
        fontFamily = mulishExtraBold,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 14.sp,
    ),
    val titleMedium: TextStyle = TextStyle(
        fontFamily = mulishBold,
        fontWeight = FontWeight.Normal,
        fontSize = 9.sp,
        lineHeight = 21.sp,
        letterSpacing = (-0.32).sp,
    ),
    val searchText: TextStyle = TextStyle(
        fontFamily = mulishBold,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),

    //Jost
    val titleScreen: TextStyle = TextStyle(
        fontFamily = jostSemiBold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 21.sp,
    ),
)