package com.decide.uikit.ui.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.decide.uikit.R
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme

@Composable
fun ButtonBackArrow(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = DecideTheme.colors.text,
    textStyle: TextStyle = DecideTheme.typography.headlineLarge,
    icon: Painter = painterResource(id = R.drawable.ic_back_arrow),
    tintIcon: Color = DecideTheme.colors.text,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick() }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            tint = tintIcon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = textStyle,
            color = textColor,
        )
    }
}

@MainPreview
@Composable
fun PreviewButtonBackArrow() {
    DecideTheme {
        Column(
            Modifier
                .fillMaxSize()
                .padding(30.dp)) {
            ButtonBackArrow(text = "Назад") {

            }
        }

    }
}