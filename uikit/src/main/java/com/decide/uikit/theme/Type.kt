package com.decide.uikit.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.decide.uikit.R

//Fira Sans
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
data class DecideTypography(
    //Fira Sans
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
        lineHeight = 12.sp,
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
//    val titleSmall: TextStyle = TextStyle(
//        fontFamily = itimRegular,
//        fontWeight = FontWeight.Normal,
//        fontSize = 22.sp,
//        lineHeight = 21.sp,
//        letterSpacing = (-0.32).sp,
//    ),
//    val subTitle: TextStyle = TextStyle(
//        fontFamily = itimRegular,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 21.sp,
//        letterSpacing = (-0.32).sp,
//    ),
//    val bodyRegular: TextStyle = TextStyle(
//        fontFamily = ralewayRegular,
//        fontWeight = FontWeight.Normal,
//        fontSize = 15.sp,
//        lineHeight = 21.sp,
//    ),
//    val bodySemibold: TextStyle = TextStyle(
//        fontFamily = ralewaySemiBold,
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 15.sp,
//        lineHeight = 21.sp,
//    ),
//    val bodyBold: TextStyle = TextStyle(
//        fontFamily = ralewayBold,
//        fontWeight = FontWeight.Bold,
//        fontSize = 15.sp,
//        lineHeight = 21.sp,
//    ),
//    val caption1Regular: TextStyle = TextStyle(
//        fontFamily = ralewayRegular,
//        fontWeight = FontWeight.Normal,
//        fontSize = 13.sp,
//        lineHeight = (15.5).sp,
//    ),
//    val caption1Semibold: TextStyle = TextStyle(
//        fontFamily = ralewaySemiBold,
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 13.sp,
//        lineHeight = (15.5).sp,
//    ),
//    val caption1Bold: TextStyle = TextStyle(
//        fontFamily = ralewayBold,
//        fontWeight = FontWeight.Bold,
//        fontSize = 13.sp,
//        lineHeight = (15.5).sp,
//    ),
//    val caption2Regular: TextStyle = TextStyle(
//        fontFamily = ralewayRegular,
//        fontWeight = FontWeight.Normal,
//        fontSize = 12.sp,
//        lineHeight = (15.5).sp,
//    ),
//    val caption2Semibold: TextStyle = TextStyle(
//        fontFamily = ralewaySemiBold,
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 12.sp,
//        lineHeight = (15.5).sp,
//    ),
//    val caption2Bold: TextStyle = TextStyle(
//        fontFamily = ralewayBold,
//        fontWeight = FontWeight.Bold,
//        fontSize = 12.sp,
//        lineHeight = (15.5).sp,
//    ),
//    val filtersRegular: TextStyle = TextStyle(
//        fontFamily = ralewayRegular,
//        fontWeight = FontWeight.Normal,
//        fontSize = 15.sp,
//        lineHeight = 20.sp,
//    ),
//    val filtersSemibold: TextStyle = TextStyle(
//        fontFamily = ralewaySemiBold,
//        fontWeight = FontWeight.SemiBold,
//        fontSize = 15.sp,
//        lineHeight = 20.sp,
//    ),
//    val filtersBold: TextStyle = TextStyle(
//        fontFamily = ralewayBold,
//        fontWeight = FontWeight.Bold,
//        fontSize = 15.sp,
//        lineHeight = 20.sp,
//    ),
//    val tabBar: TextStyle = TextStyle(
//        fontFamily = ralewayRegular,
//        fontWeight = FontWeight.Normal,
//        fontSize = 10.sp,
//        lineHeight = 12.sp,
//    ),
)

// Set of Material typography styles to start with
//val Typography = Typography(
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    ),
//    titleLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 22.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp
//    ),
//    labelSmall = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Medium,
//        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp
//    )
//
//)