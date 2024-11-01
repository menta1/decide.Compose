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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme

@Composable
fun SuccessDialog(
    title: String,
    textColor: Color = DecideTheme.colors.text,
    textStyle: TextStyle = DecideTheme.typography.titleMedium,
    backgroundColor: Color = DecideTheme.colors.container,
    containerPadding: Dp = 34.dp,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor)
                .padding(containerPadding)
        ) {
            Text(
                text = title,
                style = textStyle,
                textAlign = TextAlign.Start,
                color = textColor
            )
        }
    }
}

@MainPreview
@Composable
private fun Preview() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            SuccessDialog(
                title = "Успешно сохранено",
                onDismissRequest = {}
            )
        }
    }
}