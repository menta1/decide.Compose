package com.decide.uikit.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
fun CardQuestion(
    modifier: Modifier = Modifier,
    text: String = "",
    textStyle: TextStyle = DecideTheme.typography.titleMedium,
    textColor: Color = DecideTheme.colors.text,
    background: Color = DecideTheme.colors.containerAccent
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCardQuestion() {
    CardQuestion(text = "Nicholas Savage: You know what’s weird? That quote has been attributed to Bataille, to McLuhan, to Andy Warhol: it's one of those.")
}