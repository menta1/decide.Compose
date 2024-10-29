package com.decide.uikit.ui.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme

@Composable
fun ButtonCircle(
    modifier: Modifier = Modifier,
    background: Color = DecideTheme.colors.mainColor,
    arrowColor: Color = DecideTheme.colors.white,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(end = 9.dp)
            .size(48.dp)
            .clip(CircleShape)
            .background(background)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
            tint = arrowColor
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewButtonCircle() {
    DecideTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 39.dp),
            verticalArrangement = Arrangement.Center
        ) {
            ButtonCircle() {

            }
        }
    }
}