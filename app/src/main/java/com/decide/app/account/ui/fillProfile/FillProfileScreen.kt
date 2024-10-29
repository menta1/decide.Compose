package com.decide.app.account.ui.fillProfile

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.decide.uikit.R
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ButtonBackArrow
import com.decide.uikit.ui.buttons.ButtonEntry
import com.decide.uikit.ui.buttons.ButtonVariant
import com.decide.uikit.ui.defaultScreens.ErrorScreen
import com.decide.uikit.ui.defaultScreens.LoadingScreen
import com.decide.uikit.ui.defaultScreens.NetworkErrorScreen
import com.decide.uikit.ui.text.EditTextField
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FillProfileScreen(
    onClickBack: () -> Unit,
    onClickContinue: () -> Unit,
    onClickMainPage: () -> Unit,
) {
    val viewModel: FillProfileViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    FillProfileScreen(
        modifier = Modifier,
        state = state,
        onEvent = viewModel::onEvent,
        onClickBack = onClickBack,
        onClickContinue = onClickContinue,
        onClickMainPage = onClickMainPage,
    )

    BackHandler {
        onClickMainPage()
    }
}

@Composable
private fun FillProfileScreen(
    modifier: Modifier,
    state: FillProfileState,
    onEvent: (event: FillProfileEvent) -> Unit,
    onClickBack: () -> Unit,
    onClickContinue: () -> Unit,
    onClickMainPage: () -> Unit,
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                uri?.let {
                    onEvent(FillProfileEvent.SetUriAvatar(it))
                    imageUri = it
                }
            })

    var showDatePicker by remember { mutableStateOf(false) }

    when (state.uiState) {
        UIState.DATA_ENTRY -> {

            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp)
                        .padding(top = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            ButtonBackArrow(text = "Заполните свой профиль",
                                onClick = { onClickBack() })

                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        Box(
                            modifier = Modifier.clickable {
                                galleryLauncher.launch("image/*")
                            }, contentAlignment = Alignment.BottomEnd
                        ) {

                            Image(
                                painter = if (imageUri == null) {
                                    painterResource(id = R.drawable.placeholder_fill_profile)
                                } else {
                                    rememberAsyncImagePainter(
                                        model = imageUri
                                    )
                                },
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(114.dp),
                                contentScale = ContentScale.Crop
                            )
                            if (imageUri == null) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_select_picture),
                                    contentDescription = "select picture",
                                    tint = DecideTheme.colors.accentGreen
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Чем больше информации Вы укажите,\n тем точнее будут результаты",
                            style = DecideTheme.typography.labelSmall,
                            textAlign = TextAlign.Center,
                            color = DecideTheme.colors.gray
                        )

                        EditTextField(value = state.firstName,
                            onValueChange = { onEvent(FillProfileEvent.SetFirstName(it)) },
                            supportingText = if (state.isErrorFirstName) "Это поле не может быть пустым!" else "",
                            isError = state.isErrorFirstName,
                            labelText = "Имя*",
                            isFocus = {})

                        EditTextField(value = state.secondName,
                            onValueChange = { onEvent(FillProfileEvent.SetSecondName(it)) },
                            supportingText = "",
                            labelText = "Фамилия",
                            isFocus = {})

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged {
                                    if (it.isFocused) showDatePicker = !showDatePicker
                                },
                            value = if (state.dateOFBirth == -1L) "" else convertMillisToDate(state.dateOFBirth),
                            onValueChange = { },
                            maxLines = 1,
                            readOnly = true,
                            label = {
                                Text(
                                    text = "День рождения",
                                    style = DecideTheme.typography.titleSmall
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            trailingIcon = {
                                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "Select date"
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = DecideTheme.colors.text,
                                focusedLabelColor = DecideTheme.colors.text,
                                unfocusedLabelColor = DecideTheme.colors.gray,
                                focusedPlaceholderColor = DecideTheme.colors.gray,
                                focusedTextColor = DecideTheme.colors.text,
                                unfocusedTextColor = DecideTheme.colors.text
                            )
                        )

                        Spacer(modifier = Modifier.height(26.dp))

                        ChooseGander(state = state, onEvent = onEvent)
                        Spacer(modifier = Modifier.height(26.dp))

                        EditTextField(
                            value = state.city,
                            onValueChange = { },
                            supportingText = "",
                            labelText = "Укажите город",
                            isFocus = {
                                if (it.isFocused) onEvent(FillProfileEvent.SearchCity(""))
                            })

                        if (showDatePicker) {
                            DatePickerModal(onDateSelected = {
                                onEvent(
                                    FillProfileEvent.SetDateOFBirth(
                                        it ?: -1
                                    )
                                )
                            }, onDismiss = { showDatePicker = !showDatePicker })
                        }

                    }

                }
                ButtonEntry(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .padding(horizontal = 14.dp),
                    text = "Продолжить"
                ) {
                    onEvent(FillProfileEvent.Continue)
                }
            }

        }

        UIState.LOADING -> {
            LoadingScreen()
        }

        UIState.SUCCESS -> {
            onClickContinue()
        }

        UIState.ERROR -> {
            ErrorScreen {
                onClickMainPage()
            }
        }

        UIState.NETWORK_ERROR -> {
            NetworkErrorScreen {
                onEvent(FillProfileEvent.Continue)
            }
        }

        UIState.SEARCH_CITY -> {
            FillProfileChooseCity(
                state = state,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun ChooseGander(
    state: FillProfileState,
    onEvent: (event: FillProfileEvent) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(start = 4.dp)
                .padding(top = 4.dp),
            text = "Укажите пол",
            style = DecideTheme.typography.titleMedium,
            textAlign = TextAlign.Start,
            color = DecideTheme.colors.text
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            ButtonVariant(
                modifier = Modifier.wrapContentSize(), text = "Мужчина", onClick = {
                    onEvent(FillProfileEvent.SetGender("Мужчина"))
                }, selected = state.gender == "Мужчина"
            )

            ButtonVariant(
                modifier = Modifier.wrapContentSize(), text = "Женщина", onClick = {
                    onEvent(FillProfileEvent.SetGender("Женщина"))
                }, selected = state.gender == "Женщина"
            )
        }
    }

}

private fun convertMillisToDate(millis: Long?): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return if (millis != null) {
        formatter.format(Date(millis))
    } else {
        ""
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerModal(
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
                style = DecideTheme.typography.titleMedium,
                color = DecideTheme.colors.text
            )
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(
                text = "Отмена",
                style = DecideTheme.typography.titleMedium,
                color = DecideTheme.colors.text
            )
        }
    },
        colors = DatePickerDefaults.colors().copy(
            containerColor = DecideTheme.colors.background,

            )
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors().copy(
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
        Column(modifier = Modifier.fillMaxSize()) {
            FillProfileScreen(modifier = Modifier, state = FillProfileState(
                city = "", cities = listOf("Vjcasd", "Vjcasd", "Vjcasd", "Vjcasd", "Vjcasd")
            ), onEvent = {}, onClickBack = { }, {}, {})
        }
    }
}


@MainPreview
@Composable
private fun PreviewError() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            FillProfileScreen(
                modifier = Modifier,
                state = FillProfileState(uiState = UIState.ERROR),
                onEvent = {},
                onClickBack = { },
                {},
                {},
            )
        }
    }
}

@MainPreview
@Composable
private fun PreviewDatePicker() {
    DecideTheme(darkTheme = true) {
        Column(modifier = Modifier.fillMaxSize()) {
            DatePickerModal(onDateSelected = {}) {

            }
        }
    }
}
