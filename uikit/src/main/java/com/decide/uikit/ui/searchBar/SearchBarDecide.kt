package com.decide.uikit.ui.searchBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme

/*
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
*/

@Composable
fun SearchBarDecide(
    value: String,
    onValueChange: (String) -> Unit,
    onClickRemoveText: () -> Unit,
    hint: String = "Поиск...",
    backgroundColor: Color = DecideTheme.colors.inputWhite,
) {
    TextField(
        modifier = Modifier.fillMaxWidth()
            .border(
                width = 1.dp,
                color = backgroundColor,
                shape = RoundedCornerShape(15.dp),
            ),
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        singleLine = true,
        keyboardActions = KeyboardActions(KeyboardActions().onSearch),
        shape = RoundedCornerShape(20.dp),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (value.isNotBlank()){
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_remove_text),
                        contentDescription = "Remove search"
                    )
                }
            }
        },
        placeholder = {
            Text(
                text = hint,
                style = DecideTheme.typography.titleSmall,
                color = DecideTheme.colors.gray
            )
        },
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = DecideTheme.colors.background,
            unfocusedContainerColor = DecideTheme.colors.background,
            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
        )
        )
}

@Preview(showBackground = true)
@Composable
fun PreviewLKHYGU() {
    DecideTheme {
        var text by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier.fillMaxSize().background(DecideTheme.colors.inputBlack)
        ) {
            SearchBarDecide(
                value = text,
                onValueChange = {},
                onClickRemoveText = {}
            )
        }


    }
}
