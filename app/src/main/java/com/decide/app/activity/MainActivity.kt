package com.decide.app.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.decide.app.navigation.AppScreen
import com.decide.app.navigation.Assay
import com.decide.uikit.theme.DecideTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DecideTheme {
               AppScreen(startDestination = Assay.route)
            }
        }
    }
}