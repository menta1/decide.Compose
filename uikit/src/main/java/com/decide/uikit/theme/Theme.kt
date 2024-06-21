package com.decide.uikit.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


val LocalDecideColors = staticCompositionLocalOf {
    DecideColors()
}

val LocalInTouchTypography = staticCompositionLocalOf {
    DecideTypography()
}

object DecideTheme {
    val typography: DecideTypography
        @Composable get() = LocalInTouchTypography.current
    val colors: DecideColors
        @Composable get() = LocalDecideColors.current
}

@Composable
fun DecideTheme(
    typography: DecideTypography = DecideTheme.typography,
    colors: DecideColors = DecideTheme.colors,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalDecideColors provides colors,
        LocalInTouchTypography provides typography,
        content = content
    )
}

//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40

/* Other default colors to override
background = Color(0xFFFFFBFE),
surface = Color(0xFFFFFBFE),
onPrimary = Color.White,
onSecondary = Color.White,
onTertiary = Color.White,
onBackground = Color(0xFF1C1B1F),
onSurface = Color(0xFF1C1B1F),
*/

//
//@Composable
//fun DecideTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//
//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = Typography,
//        content = content
//    )
//}