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

    val category1: Color = category_1,
    val category2: Color = category_2,
    val category3: Color = category_3,
    val category4: Color = category_4,
    val category5: Color = category_5,
    val category6: Color = category_6,
    val category7: Color = category_7,
    val category8: Color = category_8,
    val category9: Color = category_9,
    val category10: Color = category_10,
    val category11: Color = category_11,

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
    theme: Themes = Themes.SYSTEM,
    typography: Typography = DecideTheme.typography,
    content: @Composable () -> Unit
) {
    val systemTheme = isSystemInDarkTheme()
    val color = when (theme) {
        Themes.DARK -> DarkColorPalette
        Themes.LIGHT -> LightColorPalette
        Themes.SYSTEM -> if (systemTheme) DarkColorPalette else LightColorPalette
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val controller = WindowCompat.getInsetsController(window, view)
            window.statusBarColor = color.background.toArgb()
            window.navigationBarColor = color.background.toArgb()
            controller.isAppearanceLightStatusBars =
                if (color == LightColorPalette) !systemTheme else systemTheme
            controller.isAppearanceLightNavigationBars =
                if (color == LightColorPalette) !systemTheme else systemTheme
        }
    }

    CompositionLocalProvider(
        LocalDecideColors provides color,
        LocalInTouchTypography provides typography,
        content = content
    )

}