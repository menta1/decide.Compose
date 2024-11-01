package com.decide.app.feature.profile.editProfile.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ButtonBackArrow

@Composable
fun EditProfileChooseCity(
    state: EditProfileState,
    onEvent: (event: EditProfileEvent) -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.padding(end = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                ButtonBackArrow(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .padding(top = 4.dp),
                    text = "",
                    onClick = { onEvent(EditProfileEvent.SetCity("")) })

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = state.city,
                    onValueChange = {
                        onEvent(EditProfileEvent.SearchCity(it))
                    },
                    maxLines = 1,
                    label = {
                        Text(
                            text = "Поиск по городам",
                            style = DecideTheme.typography.titleSmall,
                            color = DecideTheme.colors.unFocused
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DecideTheme.colors.text,
                        focusedLabelColor = DecideTheme.colors.text,
                        unfocusedLabelColor = DecideTheme.colors.gray,
                        focusedPlaceholderColor = DecideTheme.colors.gray,
                        focusedTextColor = DecideTheme.colors.text,
                        unfocusedTextColor = DecideTheme.colors.unFocused
                    )
                )
            }

        },
        containerColor = DecideTheme.colors.background,
        contentColor = DecideTheme.colors.background,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 14.dp)
        ) {
            items(state.cities) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable {
                            onEvent(EditProfileEvent.SetCity(it))
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it,
                        style = DecideTheme.typography.titleSmall,
                        textAlign = TextAlign.Center,
                        color = DecideTheme.colors.text
                    )
                    Spacer(Modifier.height(4.dp))
                    Icon(
                        painter = painterResource(com.decide.uikit.R.drawable.ic_arrow_right),
                        tint = DecideTheme.colors.gray,
                        contentDescription = "",
                    )
                }
                HorizontalDivider()
            }
        }
    }

}

@MainPreview
@Composable
private fun Preview() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            EditProfileChooseCity(
                state = EditProfileState(
                    city = "",
                    cities = listOf("Vjcasd", "Vjcasd", "Vjcasd", "Vjcasd", "Vjcasd")
                ),
                onEvent = {}
            )
        }
    }
}