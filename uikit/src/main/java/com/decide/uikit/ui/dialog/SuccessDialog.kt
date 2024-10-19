package com.decide.uikit.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.decide.uikit.theme.DecideTheme

@Composable
fun SuccessDialog(
    title: String,
    backgroundColor: Color = DecideTheme.colors.inputWhite,
    onDismissRequest: () -> Unit
) {

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor)
                .padding(34.dp)
        ) {
            Text(
                text = title,
                style = DecideTheme.typography.titleMedium,
                textAlign = TextAlign.Start,
                color = DecideTheme.colors.inputBlack
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, locale = "ru")
@Composable
fun PreviewSuccessDialog() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            SuccessDialog(
                title = "Успешно сохранено",
                onDismissRequest = {}
            )
        }
    }
}