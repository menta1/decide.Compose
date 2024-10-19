package com.decide.app.account.ui.fillProfile


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ButtonBackArrow

@Composable
fun FillProfileChooseCity(
    state: FillProfileState,
    onEvent: (event: FillProfileEvent) -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                ButtonBackArrow(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = "",
                    onClick = { onEvent(FillProfileEvent.SetCity("")) })

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = state.city,
                    onValueChange = {
                        onEvent(FillProfileEvent.SearchCity(it))
                    },
                    maxLines = 1,
                    label = {
                        Text(
                            text = "Поиск по городам",
                            style = DecideTheme.typography.titleSmall
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DecideTheme.colors.inputBlack,
                        focusedLabelColor = DecideTheme.colors.inputBlack,
                        unfocusedLabelColor = DecideTheme.colors.gray,
                        focusedPlaceholderColor = DecideTheme.colors.gray,
                    )
                )
            }

        },
        containerColor = DecideTheme.colors.background,
        contentColor = DecideTheme.colors.background,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(state.cities) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable {
                            onEvent(FillProfileEvent.SetCity(it))
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it,
                        style = DecideTheme.typography.titleSmall,
                        textAlign = TextAlign.Center,
                        color = DecideTheme.colors.inputBlack
                    )
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

@Preview(showBackground = true)
@Composable
fun PreviewFillProfileChooseCity() {

    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            FillProfileChooseCity(
                state = FillProfileState(
                    city = "",
                    cities = listOf("Vjcasd", "Vjcasd", "Vjcasd", "Vjcasd", "Vjcasd")
                ),
                onEvent = {}
            )
        }
    }
}
