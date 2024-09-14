package com.decide.uikit.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decide.uikit.theme.DecideTheme

@Composable
fun ErrorMessage() {
    Column(
        //  modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Произошла ошибка!",
            style = DecideTheme.typography.headlineSmall,
            color = DecideTheme.colors.inputBlack,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Попробуйте позже",
            style = DecideTheme.typography.titleLarge,
            color = DecideTheme.colors.inputBlack,
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewErrorMessage() {
    DecideTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ErrorMessage()
        }
    }
}