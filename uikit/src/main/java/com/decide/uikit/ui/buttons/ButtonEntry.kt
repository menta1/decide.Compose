package com.decide.uikit.ui.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun ButtonEntry(
    modifier: Modifier = Modifier,
    background: Color = DecideTheme.colors.buttonPrimary,
    text: String,
    textColor: Color = DecideTheme.colors.inputWhite,
    textStyle: TextStyle = DecideTheme.typography.buttonLarge,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(background)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(80.dp))
        Text(text = text, color = textColor, style = textStyle)
        ButtonCircle() {}
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewButtonEntry() {
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