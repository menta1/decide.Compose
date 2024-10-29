package com.decide.uikit.ui.defaultScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ButtonMain

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    textFirst: String = "Произошла ошибка!",
    textSecond: String = "Попробуйте позже",
    textButton: String = "Вернуться на главный экран",
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = textFirst,
            style = DecideTheme.typography.headlineSmall,
            color = DecideTheme.colors.text,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = textSecond,
            style = DecideTheme.typography.titleLarge,
            color = DecideTheme.colors.text,
        )
        Spacer(modifier = Modifier.height(24.dp))
        ButtonMain(text = textButton) {
            onClick()
        }
    }
}

@MainPreview
@Composable
private fun Preview() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            ErrorScreen() {}
        }
    }
}