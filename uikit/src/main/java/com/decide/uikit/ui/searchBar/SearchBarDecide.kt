package com.decide.uikit.ui.searchBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.decide.uikit.R
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme

@Composable
fun SearchBarDecide(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "Поиск...",
    backgroundColor: Color = DecideTheme.colors.container,
) {
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(color = backgroundColor, shape = RoundedCornerShape(15.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { focusRequester.requestFocus() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                tint = DecideTheme.colors.text
            )
            Spacer(modifier = Modifier.width(16.dp))
            BasicTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = value,
                onValueChange = onValueChange,
                maxLines = 1,
                singleLine = true,
                interactionSource = remember { MutableInteractionSource() },
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            color = DecideTheme.colors.unFocused,
                            style = DecideTheme.typography.titleSmall
                        )
                    }
                    innerTextField()
                },
                keyboardActions = KeyboardActions(KeyboardActions().onSearch),
                cursorBrush = SolidColor(DecideTheme.colors.text),
                textStyle = DecideTheme.typography.titleSmall.copy(
                    color = DecideTheme.colors.text,

                    )
            )
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = Color.Transparent, shape = CircleShape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    if (value.isNotEmpty()) {
                        onValueChange("")
                    }
                },
            contentAlignment = Alignment.CenterEnd
        ) {
            if (value.isNotBlank()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_remove_text),
                        contentDescription = "Search",
                        tint = DecideTheme.colors.text
                    )
                }
            }
        }
    }

}

@MainPreview
@Composable
private fun Preview() {
    DecideTheme {
        var text by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            SearchBarDecide(
                value = text,
                onValueChange = {
                    text = it
                },
            )
        }
    }
}