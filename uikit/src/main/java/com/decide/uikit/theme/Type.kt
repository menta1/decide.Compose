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
)