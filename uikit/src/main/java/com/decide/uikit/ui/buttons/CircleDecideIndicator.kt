package com.decide.uikit.ui.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.decide.uikit.theme.DecideTheme

@Composable
fun CircleDecideIndicator() {
    CircularProgressIndicator(
        color = DecideTheme.colors.accentYellow
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCircleDecideIndicator() {
    DecideTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircleDecideIndicator()
        }
    }
}