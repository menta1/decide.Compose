package com.decide.uikit.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme

@Composable
fun CardQuestion(
    modifier: Modifier = Modifier,
    text: String = "",
    textStyle: TextStyle = DecideTheme.typography.bodyMedium,
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

@MainPreview
@Composable
fun PreviewCardQuestion() {
    DecideTheme {
        Column(
            Modifier
                .fillMaxSize()
                .padding(vertical = 14.dp)) {
            CardQuestion(
                text = "Nicholas Savage: You know w" +
                        "hat’s weird? That quote has been attributed to Bata" +
                        "ille, to McLuhan, to Andy Warhol: it's one of those."
            )
        }
    }
}