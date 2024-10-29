package com.decide.uikit.ui.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import com.decide.uikit.theme.DecideTheme

@Composable
fun EditTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: String,
    supportingStyle: TextStyle = DecideTheme.typography.titleSmall,
    supportingColor: Color = DecideTheme.colors.error,
    labelText: String,
    labelStyle: TextStyle = DecideTheme.typography.titleSmall,
    labelColor: Color = DecideTheme.colors.text,
    focusedBorderColor: Color = DecideTheme.colors.text,
    focusedLabelColor: Color = DecideTheme.colors.text,
    unfocusedLabelColor: Color = DecideTheme.colors.gray,
    errorTextColor: Color = DecideTheme.colors.text,
    focusedPlaceholderColor: Color = DecideTheme.colors.gray,
    focusedTextColor: Color = DecideTheme.colors.text,
    unfocusedTextColor: Color = DecideTheme.colors.text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    isFocus: (FocusState) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { isFocus(it) },
        value = value,
        onValueChange = onValueChange,
        visualTransformation = visualTransformation,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions,
        isError = isError,
        trailingIcon = trailingIcon,
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
                style = labelStyle,
                color = labelColor
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            errorTextColor = errorTextColor,
            errorBorderColor = supportingColor,
            focusedPlaceholderColor = focusedPlaceholderColor,
            focusedBorderColor = focusedBorderColor,
            focusedLabelColor = focusedLabelColor,
            unfocusedLabelColor = unfocusedLabelColor,
            focusedTextColor = focusedTextColor,
            unfocusedTextColor = unfocusedTextColor,
        )
    )
}