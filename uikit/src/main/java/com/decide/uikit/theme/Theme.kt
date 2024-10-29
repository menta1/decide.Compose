package com.decide.uikit.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val LightColorPalette = DecideColors(
    background = uiBackgroundLight,
    ripple = rippleLight,
    text = textLight,
    textReverse = textDark,
    container = containerLight,
    containerAccent = containerAccentLight,
    mainColor = colorPrimary,
    unFocused = unFocused,
    unFocused40 = unFocused40,
    black = black,
    white = white,

    mainGreen40 = mainColorGreen40,
    accentYellow = accentColorYellow,
    accentGreen = accentColorGreen,
    accentPink = accentColorPink,
    buttonPrimary = colorPrimary,
    unableElementLight = unableElementsColorLight,

    gray = unselectedColor,
    textHelp = mainColorGreen40,
    textLink = mainColorGreen40,
    error = functionalRed,
    isDark = false
)

private val DarkColorPalette = DecideColors(
    background = uiBackgroundDark,
    ripple = rippleDark,
    text = textDark,
    textReverse = textLight,
    container = containerDark,
    containerAccent = containerAccentDark,
    mainColor = colorPrimary,
    unFocused = unFocused,
    unFocused40 = unFocused40,
    black = black,
    white = white,


    mainGreen40 = mainColorGreen40,
    accentYellow = accentColorYellow,
    accentGreen = accentColorGreen,
    accentPink = accentColorPink,
    buttonPrimary = colorPrimary,
    unableElementLight = unableElementsColorLight,

    gray = unselectedColor,
    textHelp = mainColorGreen40,
    textLink = mainColorGreen40,
    error = functionalRedDark,
    isDark = true
)


@Immutable
data class DecideColors(
    val background: Color = Color.Unspecified,
    val ripple: Color = Color.Unspecified,
    val text: Color = Color.Unspecified,
    val textReverse: Color = Color.Unspecified,
    val container: Color = Color.Unspecified,
    val containerAccent: Color = Color.Unspecified,
    val mainColor: Color = Color.Unspecified,
    val unFocused: Color = Color.Unspecified,
    val unFocused40: Color = Color.Unspecified,
    val black: Color = Color.Unspecified,
    val white: Color = Color.Unspecified,


    val mainGreen40: Color = Color.Unspecified,
    val accentYellow: Color = Color.Unspecified,
    val accentGreen: Color = Color.Unspecified,
    val accentPink: Color = Color.Unspecified,
    val buttonPrimary: Color = Color.Unspecified,
    val unableElementLight: Color = Color.Unspecified,

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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = color.background.toArgb()
            window.navigationBarColor = color.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalDecideColors provides color,
        LocalInTouchTypography provides typography,
        content = content
    )

}