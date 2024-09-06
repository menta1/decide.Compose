package com.decide.uikit.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.decide.uikit.theme.DecideTheme

@Composable
fun EditTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    supportingText: String,
    supportingStyle: TextStyle = DecideTheme.typography.titleSmall,
    supportingColor: Color = DecideTheme.colors.error,
    labelText: String,
    labelStyle: TextStyle = DecideTheme.typography.titleSmall,
    labelColor: Color = DecideTheme.colors.error,
    focusedBorderColor: Color = DecideTheme.colors.inputBlack,
    focusedLabelColor: Color = DecideTheme.colors.inputBlack,
    unfocusedLabelColor: Color = DecideTheme.colors.gray,
    isFocus: (FocusState) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { isFocus(it) },
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        isError = isError,
        supportingText = {
            Text(
                text = supportingText,
                style = supportingStyle,
                color = supportingColor
            )
        },
        label = {
            Text(
                text = labelText,
                style = labelStyle
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = focusedBorderColor,
            focusedLabelColor = focusedLabelColor,
            unfocusedLabelColor = unfocusedLabelColor
        )
    )
}