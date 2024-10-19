package com.decide.app.feature.profile.editProfile.ui

import android.net.Uri
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.decide.app.feature.defaultScreens.ErrorScreen
import com.decide.app.feature.defaultScreens.LoadingScreen
import com.decide.app.feature.defaultScreens.NetworkErrorScreen
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.EditTextField
import com.decide.uikit.ui.buttons.ButtonBackArrow
import com.decide.uikit.ui.buttons.ButtonEntry
import com.decide.uikit.ui.buttons.ButtonVariant
import com.decide.uikit.ui.dialog.SuccessDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EditProfileScreen(
    onClickBack: () -> Unit,
) {

    val viewModel: EditProfileScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    EditProfileScreen(
        modifier = Modifier,
        state = state,
        onEvent = viewModel::onEvent,
        onClickBack = onClickBack
    )
}

@Composable
fun EditProfileScreen(
    modifier: Modifier,
    state: EditProfileState,
    onEvent: (event: EditProfileEvent) -> Unit,
    onClickBack: () -> Unit,
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                uri?.let {
                    onEvent(EditProfileEvent.SetUriAvatar(it))
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
                            ButtonBackArrow(text = "Редактировать профиль",
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
                            color = DecideTheme.colors.inputBlack
                        )

                        EditTextField(value = state.firstName,
                            onValueChange = { onEvent(EditProfileEvent.SetFirstName(it)) },
                            supportingText = if (state.isErrorFirstName) "Это поле не может быть пустым!" else "",
                            isError = state.isErrorFirstName,
                            labelText = "Имя*",
                            isFocus = {})

                        EditTextField(value = state.secondName,
                            onValueChange = { onEvent(EditProfileEvent.SetSecondName(it)) },
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
                                focusedBorderColor = DecideTheme.colors.inputBlack,
                                focusedLabelColor = DecideTheme.colors.inputBlack,
                                unfocusedLabelColor = DecideTheme.colors.gray,
                                focusedPlaceholderColor = DecideTheme.colors.gray,
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
                                if (it.isFocused) onEvent(EditProfileEvent.SearchCity(""))
                            })

                        if (showDatePicker) {
                            DatePickerModal(onDateSelected = {
                                onEvent(
                                    EditProfileEvent.SetDateOFBirth(
                                        it ?: -1
                                    )
                                )
                            }, onDismiss = { showDatePicker = !showDatePicker })
                        }

                    }

                }
                ButtonEntry(
                    modifier = Modifier
                        .padding(bottom = 12.dp),
                    text = "Сохранить"
                ) {
                    onEvent(EditProfileEvent.Continue)
                }
            }

            if (state.showSuccessDialog) {
                SuccessDialog(
                    title = "Успешно сохранено",
                    onDismissRequest = { onClickBack() }
                )
            }

        }

        UIState.LOADING -> {
            LoadingScreen()
        }


        UIState.ERROR -> {
            ErrorScreen {
                onClickBack()
            }
        }

        UIState.SUCCESS -> {
            onClickBack()
        }

        UIState.NETWORK_ERROR -> {
            NetworkErrorScreen {
                onEvent(EditProfileEvent.Continue)
            }
        }

        UIState.SEARCH_CITY -> {
            EditProfileChooseCity(
                state = state,
                onEvent = onEvent
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewFillProfileScreen() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            EditProfileScreen(
                modifier = Modifier,
                state = EditProfileState(
                    city = "",
                    cities = listOf("Vjcasd", "Vjcasd", "Vjcasd", "Vjcasd", "Vjcasd")
                ),
                onEvent = {},
                onClickBack = {}
            )
        }
    }
}

@Composable
fun ChooseGander(
    state: EditProfileState,
    onEvent: (event: EditProfileEvent) -> Unit,
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
            color = DecideTheme.colors.inputBlack
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            ButtonVariant(
                modifier = Modifier.wrapContentSize(), text = "Мужчина", onClick = {
                    onEvent(EditProfileEvent.SetGender("Мужчина"))
                }, selected = state.gender == "Мужчина"
            )

            ButtonVariant(
                modifier = Modifier.wrapContentSize(), text = "Женщина", onClick = {
                    onEvent(EditProfileEvent.SetGender("Женщина"))
                }, selected = state.gender == "Женщина"
            )
        }
    }

}

fun convertMillisToDate(millis: Long?): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return if (millis != null) {
        formatter.format(Date(millis))
    } else {
        ""
    }
}

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
                style = DecideTheme.typography.titleMedium,
                color = DecideTheme.colors.inputBlack
            )
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(
                text = "Отмена",
                style = DecideTheme.typography.titleMedium,
                color = DecideTheme.colors.inputBlack
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
                selectedDayContentColor = DecideTheme.colors.inputWhite,
                selectedDayContainerColor = DecideTheme.colors.accentGreen,
                todayDateBorderColor = DecideTheme.colors.accentYellow,
                todayContentColor = DecideTheme.colors.inputBlack
            )
        )
    }
}


