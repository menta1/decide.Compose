package com.decide.uikit.ui.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme

@Composable
fun ItemSettings(
    modifier: Modifier = Modifier,
    background: Color = DecideTheme.colors.inputWhite,
    text: String,
    textColor: Color = DecideTheme.colors.inputBlack,
    textStyle: TextStyle = DecideTheme.typography.bodyMedium,
    iconItem: Painter = painterResource(id = R.drawable.ic_bookmark_unselected),
    tintItem: Color = DecideTheme.colors.gray,
    iconArrow: Painter = painterResource(id = R.drawable.ic_arrow_right),
    tintArrow: Color = DecideTheme.colors.gray,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .padding(end = 8.dp),
                painter = iconItem,
                contentDescription = null,
                tint = tintItem
            )
            Text(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .padding(vertical = 4.dp),
                text = text,
                color = textColor,
                style = textStyle
            )
        }
        Icon(
            modifier = Modifier.padding(end = 4.dp),
            painter = iconArrow,
            contentDescription = null,
            tint = tintArrow
        )

    }

}

@Preview(showSystemUi = true)
@Composable
fun PreviewItemSettings() {
    Column(
        modifier = Modifier
            .background(DecideTheme.colors.inputBlack)
            .fillMaxSize()
            .padding(horizontal = 39.dp), verticalArrangement = Arrangement.Center
    ) {
        ItemSettings(text = "Редактировать профиль") {

        }
    }
}