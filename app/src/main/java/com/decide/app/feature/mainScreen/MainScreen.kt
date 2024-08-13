package com.decide.app.feature.mainScreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val mainViewModel = hiltViewModel<MainScreenViewModel>()
    val navController = rememberNavController()

//    Scaffold {
//
//    }
}