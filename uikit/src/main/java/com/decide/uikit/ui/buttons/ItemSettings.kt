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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
    background: Color = DecideTheme.colors.container,
    text: String,
    switchable: Boolean = false,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    textColor: Color = DecideTheme.colors.text,
    textStyle: TextStyle = DecideTheme.typography.bodyMedium,
    iconItem: Painter = painterResource(id = R.drawable.ic_bookmark_unselected),
    tintItem: Color = DecideTheme.colors.mainColor,
    iconArrow: Painter = painterResource(id = R.drawable.ic_arrow_right),
    tintArrow: Color = DecideTheme.colors.gray,
    onClick: () -> Unit,
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
                .padding(vertical = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
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
        if (switchable) {
            Switch(
                modifier = Modifier.padding(end = 4.dp),
                checked = checked,
                onCheckedChange = {
                    onCheckedChange(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = DecideTheme.colors.accentGreen,
                    checkedTrackColor = DecideTheme.colors.accentYellow,
                    checkedBorderColor = DecideTheme.colors.accentYellow,
                    uncheckedBorderColor = DecideTheme.colors.gray,
                    uncheckedThumbColor = DecideTheme.colors.gray,
                    uncheckedTrackColor = DecideTheme.colors.background,
                )
            )
        } else {
            Icon(
                modifier = Modifier.padding(end = 4.dp),
                painter = iconArrow,
                contentDescription = null,
                tint = tintArrow
            )
        }

    }

}

@Preview(showSystemUi = true)
@Composable
fun PreviewItemSettings() {
    DecideTheme {
        Column(
            modifier = Modifier
                .background(DecideTheme.colors.accentGreen)
                .fillMaxSize()
                .padding(horizontal = 39.dp),
            verticalArrangement = Arrangement.Center
        ) {
            ItemSettings(text = "Редактировать профиль") {

            }
        }
    }

}