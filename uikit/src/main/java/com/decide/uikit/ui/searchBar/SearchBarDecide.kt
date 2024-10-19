package com.decide.uikit.ui.searchBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme

@Composable
fun SearchBarDecide(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "Поиск...",
    backgroundColor: Color = DecideTheme.colors.inputWhite,
) {
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .border(
                width = 0.2.dp,
                color = DecideTheme.colors.gray,
                shape = RoundedCornerShape(15.dp),
            )
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

        Row {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                tint = DecideTheme.colors.inputBlack
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
                            color = Color.Gray.copy(alpha = 0.5f),
                            style = DecideTheme.typography.titleMedium
                        )
                    }
                    innerTextField()
                },
                keyboardActions = KeyboardActions(KeyboardActions().onSearch),
                cursorBrush = SolidColor(DecideTheme.colors.inputBlack),
                textStyle = DecideTheme.typography.titleMedium.copy(
                    color = DecideTheme.colors.inputBlack
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
                        tint = DecideTheme.colors.inputBlack
                    )
                }
            }
        }
    }

}


@Preview
@Composable
fun PreviewSearchBar() {
    DecideTheme {
        var text by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DecideTheme.colors.inputBlack)
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

//
//@Composable
//fun SearchBarDecide(
//    value: String,
//    onValueChange: (String) -> Unit,
//    onClickRemoveText: () -> Unit,
//    hint: String = "Поиск...",
//    backgroundColor: Color = DecideTheme.colors.inputWhite,
//) {
//    TextField(
//        modifier = Modifier
//            .fillMaxWidth()
//            .border(
//                width = 1.dp,
//                color = backgroundColor,
//                shape = RoundedCornerShape(15.dp),
//            ),
//        value = value,
//        onValueChange = onValueChange,
//        maxLines = 1,
//        singleLine = true,
//        keyboardActions = KeyboardActions(KeyboardActions().onSearch),
//        shape = RoundedCornerShape(20.dp),
//        leadingIcon = {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_search), contentDescription = "Search"
//            )
//        },
//        trailingIcon = {
//            if (value.isNotBlank()) {
//                IconButton(onClick = { onValueChange("") }) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_remove_text),
//                        contentDescription = "Remove search"
//                    )
//                }
//            }
//        },
//        placeholder = {
//            Text(
//                text = hint,
//                style = DecideTheme.typography.titleSmall,
//                color = DecideTheme.colors.gray
//            )
//        },
//        colors = TextFieldDefaults.colors().copy(
//            focusedContainerColor = DecideTheme.colors.background,
//            unfocusedContainerColor = DecideTheme.colors.background,
////            focusedIndicatorColor = Color.Transparent,
////            unfocusedIndicatorColor = Color.Transparent,
//        )
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewLKHYGU() {
//    DecideTheme {
//        var text by remember {
//            mutableStateOf("")
//        }
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(DecideTheme.colors.inputBlack)
//        ) {
//            SearchBarDecide(value = text, onValueChange = {}, onClickRemoveText = {})
//        }
//
//
//    }
//}
