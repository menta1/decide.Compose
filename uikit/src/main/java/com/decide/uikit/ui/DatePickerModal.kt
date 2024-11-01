package com.decide.uikit.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = {
            onDateSelected(datePickerState.selectedDateMillis)
            onDismiss()
        }) {
            Text(
                text = "Выбрать",
                style = DecideTheme.typography.titleSmall,
                color = DecideTheme.colors.text
            )
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(
                text = "Отмена",
                style = DecideTheme.typography.titleSmall,
                color = DecideTheme.colors.text
            )
        }
    }, colors = DatePickerDefaults.colors().copy(
        containerColor = DecideTheme.colors.background
    )
    ) {
        DatePicker(
            state = datePickerState, colors = DatePickerDefaults.colors().copy(
                containerColor = DecideTheme.colors.background,
                selectedDayContentColor = DecideTheme.colors.text,
                selectedDayContainerColor = DecideTheme.colors.accentGreen,
                todayDateBorderColor = DecideTheme.colors.accentYellow,
                todayContentColor = DecideTheme.colors.text,
                titleContentColor = DecideTheme.colors.text,
                headlineContentColor = DecideTheme.colors.text,
                weekdayContentColor = DecideTheme.colors.text,
                navigationContentColor = DecideTheme.colors.text,
                yearContentColor = DecideTheme.colors.text,
                selectedYearContainerColor = DecideTheme.colors.unFocused,
                dayContentColor = DecideTheme.colors.unFocused,
            )
        )
    }
}

@MainPreview
@Composable
private fun Preview() {
    DecideTheme {
        Column(Modifier.fillMaxSize()) {
            DatePickerModal({}, {})
        }
    }
}
