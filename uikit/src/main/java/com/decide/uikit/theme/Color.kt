package com.decide.uikit.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color


val mainColorBlue = Color(0xFFF5F9FF)
val mainColorGreen = Color(0xFF417D88)
val mainColorGreen40 = Color(0x66417D88)

val accentColorYellow = Color(0xFFFFCF5C)
val accentColorGreen = Color(0xFF00C48C)
val accentColorPink = Color(0xFFFF647C)
val accentColorOrange = Color(0xFFFF6B00)
val buttonColorPrimary = Color(0xFF4EAEFF)

val unableElementsColorLight = Color(0xFFEBF4F1)
val unableElementsColorDark = Color(0xFFD9D9D9)

val errorRedColor = Color(0xFFE22749)


val accentColorGreen30 = Color(0x4D338C8B)
val accentColorGreen50 = Color(0x80338C8B)
val accentColorBeige = Color(0xFFFFE7B8)

val textColorBlue = Color(0xFF1F304F)
val textColorBlue50 = Color(0x801F304F)

val textColorGreen = Color(0xFF0B4A56)
val textColorGreen40 = Color(0x66417D88)


val inputColorWhite = Color(0xFFFFFFFF)
val inputColorBlack = Color(0xFF202244)


val unselectedColor = Color(0xFFB4BDC4)
val inputColor85 = Color(0xD9FFFFFF)


@Immutable
data class DecideColors(
    val mainBlue: Color = mainColorBlue,
    val mainGreen: Color = mainColorGreen,
    val mainGreen40: Color = mainColorGreen40,
    val darkGreen: Color = Color(0xFF356C76),
    val accentYellow: Color = accentColorYellow,
    val accentGreen: Color = accentColorGreen,
    val accentPink: Color = accentColorPink,
    val buttonPrimary: Color = buttonColorPrimary,
    val accentGreen30: Color = accentColorGreen30,
    val accentGreen50: Color = accentColorGreen50,
    val textBlue: Color = textColorBlue,
    val textBlue50: Color = textColorBlue50,
    val textGreen: Color = textColorGreen,
    val textGreen40: Color = textColorGreen40,
    val unableElementLight: Color = unableElementsColorLight,
    val unableElementDark: Color = unableElementsColorDark,
    val inputWhite: Color = inputColorWhite,
    val inputBlack: Color = inputColorBlack,
    val gray: Color = unselectedColor,
    val input85: Color = inputColor85,
    val transparent: Color = Color.Transparent,
    val accentBeige: Color = accentColorBeige,
    val errorRed: Color = errorRedColor,
    val errorMaroonColor: Color = Color(0xFFA61B34),
)


//val Purple80 = Color(0xFFD0BCFF)
//val PurpleGrey80 = Color(0xFFCCC2DC)
//val Pink80 = Color(0xFFEFB8C8)
//
//val Purple40 = Color(0xFF6650a4)
//val PurpleGrey40 = Color(0xFF625b71)
//val Pink40 = Color(0xFF7D5260)