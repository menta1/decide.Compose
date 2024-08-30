package com.decide.app.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.decide.app.navigation.AppNavScreen
import com.decide.app.navigation.Assay
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.theme.uiBackground
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initApp()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(uiBackground.toArgb(), Color.Black.toArgb())
        )
        setContent {
            DecideTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    color = DecideTheme.colors.background
                ) {
                    AppNavScreen(startDestination = Assay.route)
                }
            }
        }
    }
}