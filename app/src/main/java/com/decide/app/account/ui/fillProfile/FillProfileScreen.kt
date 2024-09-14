package com.decide.app.account.ui.fillProfile

import android.net.Uri
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.decide.app.R
import com.decide.app.feature.defaultScreens.ErrorScreen
import com.decide.app.feature.defaultScreens.LoadingScreen
import com.decide.app.feature.defaultScreens.NetworkErrorScreen
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.EditTextField
import com.decide.uikit.ui.buttons.ButtonBackArrow
import com.decide.uikit.ui.buttons.ButtonEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FillProfileScreen(
    onClickBack: () -> Unit,
    onClickContinue: () -> Unit,
    onClickMainPage: () -> Unit
) {
    val viewModel: FillProfileViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    FillProfileScreen(
        modifier = Modifier,
        state = state,
        onEvent = viewModel::onEvent,
        onClickBack = onClickBack,
        onClickContinue = onClickContinue,
        onClickMainPage = onClickMainPage
    )

    object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onClickMainPage()
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FillProfileScreen(
    modifier: Modifier,
    state: FillProfileState,
    onEvent: (event: FillProfileEvent) -> Unit,
    onClickBack: () -> Unit,
    onClickContinue: () -> Unit,
    onClickMainPage: () -> Unit
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                onEvent(FillProfileEvent.SetUriAvatar(it))
                imageUri = it
            }
        }
    )

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    when (state.uiState) {
        UIState.DATA_ENTRY -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
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
                        ButtonBackArrow(
                            text = "Заполните свой профиль",
                            onClick = { onClickBack() })

                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    Box(
                        modifier = Modifier.clickable {
                            galleryLauncher.launch("image/*")

                        },
                        contentAlignment = Alignment.BottomEnd
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

                    EditTextField(
                        value = state.firstName,
                        onValueChange = { onEvent(FillProfileEvent.SetFirstName(it)) },
                        supportingText = if (state.isErrorFirstName) "Это поле не может быть пустым!" else "",
                        isError = state.isErrorFirstName,
                        labelText = "Имя*",
                        isFocus = {}
                    )
                    EditTextField(
                        value = state.secondName,
                        onValueChange = { onEvent(FillProfileEvent.SetSecondName(it)) },
                        supportingText = "",
                        labelText = "Фамилия",
                        isFocus = {}
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .onFocusChanged { if (it.isFocused) showDatePicker = !showDatePicker },
                        value = state.dateOFBirth,
                        onValueChange = { },
                        maxLines = 1,
                        readOnly = true,
                        label = {
                            Text(
                                text = "День рождения",
                                style = DecideTheme.typography.titleSmall
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

                    if (showDatePicker) {
                        DatePickerModal(
                            onDateSelected = {
                                onEvent(
                                    FillProfileEvent.SetDateOFBirth(
                                        convertMillisToDate(it)
                                    )
                                )
                            },
                            onDismiss = { showDatePicker = !showDatePicker }
                        )
                    }


                }

                ButtonEntry(text = "Продолжить") {
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFillProfileScreen() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            FillProfileScreen(
                modifier = Modifier,
                state = FillProfileState(),
                onEvent = {},
                onClickBack = { },
                {}, {}
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewError() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            FillProfileScreen(
                modifier = Modifier,
                state = FillProfileState(uiState = UIState.ERROR),
                onEvent = {},
                onClickBack = { },
                {}, {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }


}

@Preview(showBackground = true, showSystemUi = true, locale = "ru")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked() {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("DOB") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, locale = "ru")
@Composable
fun PreviewDatePicker() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            DatePickerModal(onDateSelected = {}) {

            }
        }
    }
}

