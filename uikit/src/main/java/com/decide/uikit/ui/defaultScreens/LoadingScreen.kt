package com.decide.uikit.ui.defaultScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.CircleDecideIndicator

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircleDecideIndicator()
    }
}


@MainPreview
@Composable
private fun Preview() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            LoadingScreen()
        }
    }
}