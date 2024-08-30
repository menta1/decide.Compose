package com.decide.uikit.ui.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decide.uikit.theme.DecideTheme

@Composable
fun ButtonMain(
    modifier: Modifier = Modifier,
    background: Color = DecideTheme.colors.buttonPrimary,
    backgroundIsNotActive: Color = DecideTheme.colors.gray,
    isActive: Boolean = true,
    text: String,
    textColor: Color = DecideTheme.colors.inputWhite,
    textStyle: TextStyle = DecideTheme.typography.titleMedium,
    onClick: () -> Unit
) {

    val custom = modifier
        .fillMaxWidth()
        .height(60.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(
            if (isActive) background else backgroundIsNotActive
        )

    val combinedModifier = if (isActive) {
        custom.clickable { onClick() }
    } else {
        custom
    }

    Box(
        modifier = combinedModifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = textColor, style = textStyle)
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMainButton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 39.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ButtonMain(text = "Начать") {

        }
    }
}