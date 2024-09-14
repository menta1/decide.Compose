package com.decide.app.feature.defaultScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.ErrorMessage
import com.decide.uikit.ui.buttons.ButtonMain

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    text: String = "Вернуться на главный экран",
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
        ErrorMessage()
        Spacer(modifier = Modifier.height(24.dp))
        ButtonMain(text = text) {
            onClick()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewError() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            ErrorScreen() {}
        }
    }
}