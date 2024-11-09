package com.decide.uikit.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
fun BackDialog(
    title: String = "Хотите вернуться на главное меню?",
    textColor: Color = DecideTheme.colors.text,
    textStyle: TextStyle = DecideTheme.typography.titleSmall,
    backgroundColor: Color = DecideTheme.colors.container,
    containerPadding: Dp = 34.dp,
    onDismissRequest: () -> Unit,
    onConfirmRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier.clickable(
                        indication = ripple(
                            radius = 10.dp, color = DecideTheme.colors.text
                        ), interactionSource = remember { MutableInteractionSource() },
                        onClick = { onDismissRequest() }
                    ),
                    text = "Нет",
                    style = textStyle,
                    textAlign = TextAlign.Start,
                    color = textColor
                )
                Spacer(Modifier.width(24.dp))
                Text(
                    modifier = Modifier.clickable(
                        indication = ripple(
                            radius = 10.dp, color = DecideTheme.colors.text
                        ), interactionSource = remember { MutableInteractionSource() },
                        onClick = { onConfirmRequest() }
                    ),
                    text = "Да",
                    style = textStyle,
                    textAlign = TextAlign.Start,
                    color = textColor
                )
            }
        }
    }
}

@MainPreview
@Composable
private fun Preview() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            BackDialog(
                title = "Хотите вернуться на главное меню?",
                onDismissRequest = {},
                onConfirmRequest = {}
            )
        }
    }
}