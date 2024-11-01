package com.decide.uikit.ui.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme


@Composable
fun ButtonEntry(
    modifier: Modifier = Modifier,
    background: Color = DecideTheme.colors.buttonPrimary,
    text: String,
    textColor: Color = DecideTheme.colors.white,
    textStyle: TextStyle = DecideTheme.typography.headlineMedium,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(background)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
    }
}

@MainPreview
@Composable
fun PreviewButtonEntry() {
    DecideTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 39.dp),
            verticalArrangement = Arrangement.Center
        ) {
            ButtonEntry(text = "Регистрация") {

            }
        }
    }
}