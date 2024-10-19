package com.decide.uikit.ui.buttons

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonItem(
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
    Row {
//        Icon(
//            painter = iconSelected,
//
//        )
    }
}