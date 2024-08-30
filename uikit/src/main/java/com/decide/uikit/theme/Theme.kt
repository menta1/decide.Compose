package com.decide.uikit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


private val LightColorPalette = DecideColors(
    background = uiBackground,
    mainGreen40 = mainColorGreen40,
    accentYellow = accentColorYellow,
    accentGreen = accentColorGreen,
    accentPink = accentColorPink,
    buttonPrimary = buttonColorPrimary,
    unableElementLight = unableElementsColorLight,
    inputWhite = inputColorWhite,
    inputBlack = inputColorBlack,
    gray = unselectedColor,
    textHelp = mainColorGreen40,
    textLink = mainColorGreen40,
    error = functionalRed,
    isDark = false
)

private val DarkColorPalette = DecideColors(
    background = uiBackground,
    mainGreen40 = mainColorGreen40,
    accentYellow = accentColorYellow,
    accentGreen = accentColorGreen,
    accentPink = accentColorPink,
    buttonPrimary = buttonColorPrimary,
    unableElementLight = unableElementsColorLight,
    inputWhite = inputColorWhite,
    inputBlack = inputColorBlack,
    gray = unselectedColor,
    textHelp = mainColorGreen40,
    textLink = mainColorGreen40,
    error = functionalRedDark,
    isDark = true
)


@Immutable
data class DecideColors(
    val background: Color = Color.Unspecified,
    val mainGreen40: Color = Color.Unspecified,
    val accentYellow: Color = Color.Unspecified,
    val accentGreen: Color = Color.Unspecified,
    val accentPink: Color = Color.Unspecified,
    val buttonPrimary: Color = Color.Unspecified,
    val unableElementLight: Color = Color.Unspecified,
    val inputWhite: Color = Color.Unspecified,
    val inputBlack: Color = Color.Unspecified,
    val gray: Color = Color.Unspecified,
    val textHelp: Color = Color.Unspecified,
    val textLink: Color = Color.Unspecified,
    val error: Color = Color.Unspecified,
    val notificationBadge: Color = error,
    val isDark: Boolean = false
)


val LocalDecideColors = staticCompositionLocalOf {
    DecideColors()
}

val LocalInTouchTypography = staticCompositionLocalOf {
    DecideTypography
}


object DecideTheme {
    val typography: Typography
        @Composable get() = LocalInTouchTypography.current
    val colors: DecideColors
        @Composable get() = LocalDecideColors.current
}

@Composable
fun DecideTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: Typography = DecideTheme.typography,
    content: @Composable () -> Unit
) {
    val color = if (darkTheme) DarkColorPalette else LightColorPalette

    CompositionLocalProvider(
        LocalDecideColors provides color,
        LocalInTouchTypography provides typography,
        content = content
    )
}