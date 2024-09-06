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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme

@Composable
fun ButtonVariant(
    modifier: Modifier = Modifier,
    background: Color = DecideTheme.colors.inputWhite,
    text: String,
    textColor: Color = DecideTheme.colors.inputBlack,
    textStyle: TextStyle = DecideTheme.typography.bodyMedium,
    selected: Boolean = false,
    iconSelected: Painter = painterResource(id = R.drawable.ic_checkbox_selected),
    tintIconSelected: Color = DecideTheme.colors.accentGreen,
    iconUnselected: Painter = painterResource(id = R.drawable.ic_checkbox_unselected),
    tintIconUnselected: Color = DecideTheme.colors.unableElementLight,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(vertical = 17.dp)
                .padding(start = 20.dp, end = 16.dp),
            painter = if (selected) iconSelected else iconUnselected,
            contentDescription = null,
            tint = if (selected) tintIconSelected else tintIconUnselected
        )
        Text(
            modifier = Modifier.padding(end = 20.dp).padding(vertical = 4.dp),
            text = text,
            color = textColor,
            style = if(selected) textStyle.copy(fontSize = 15.sp) else textStyle
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewButtonVariant() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 39.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ButtonVariant(text = "Начать") {

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewButtonVariantSelected() {

    var selected by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 39.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ButtonVariant(text = "категорически не согласна с тем, что он делает и говорит в данной ситуации; активно выражаю несогласие и настаиваю на своем", selected = selected) {
            selected = !selected
        }
    }
}