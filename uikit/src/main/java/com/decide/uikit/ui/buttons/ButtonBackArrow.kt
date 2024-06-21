package com.decide.uikit.ui.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme

@Composable
fun ButtonBackArrow(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = DecideTheme.colors.inputBlack,
    textStyle: TextStyle = DecideTheme.typography.titleScreen,
    icon: Painter = painterResource(id = R.drawable.ic_back_arrow),
    tintIcon: Color = DecideTheme.colors.inputBlack,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            tint = tintIcon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = text,
            style = textStyle,
            color = textColor,
        )
    }
}