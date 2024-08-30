package com.decide.uikit.ui.searchBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decide.uikit.theme.DecideTheme

@Composable
fun SearchBarDecide(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "Поиск...",
    backgroundColor: Color = DecideTheme.colors.inputWhite,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(15.dp))
            .border(
                width = 1.dp,
                color = backgroundColor,
                shape = RoundedCornerShape(15.dp),
            ),
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(DecideTheme.colors.inputBlack),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        maxLines = 1,
                        style = DecideTheme.typography.titleMedium.copy(
                            color = DecideTheme.colors.gray
                        )
                    )

                }
                innerTextField()
            },
            textStyle = DecideTheme.typography.titleMedium.copy(
                color = DecideTheme.colors.inputBlack
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 22.dp)
        )

    }
}

@Preview
@Composable
fun PreviewSearchBar() {
    DecideTheme {
        var text by remember { mutableStateOf("") }
        SearchBarDecide(
            value = text,
            onValueChange = {
                text = it
            },
            hint = "Hint text",
            modifier = Modifier.padding(45.dp)
        )
    }
}