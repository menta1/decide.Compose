package com.decide.app.account.ui.registration

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.R
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ButtonEntry
import com.decide.uikit.ui.defaultScreens.ErrorScreen
import com.decide.uikit.ui.defaultScreens.LoadingScreen
import com.decide.uikit.ui.defaultScreens.NetworkErrorScreen
import com.decide.uikit.ui.text.EditTextField

@Composable
fun RegistrationScreen(
    onClickFillProfile: () -> Unit,
    onClickBack: () -> Unit,
    onClickLogin: () -> Unit,
    onClickMainPage: () -> Unit,
    skip: () -> Unit
) {

    val viewModel: RegistrationViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegistrationScreen(
        modifier = Modifier,
        onClickLogin = onClickLogin,
        state = state,
        onEvent = viewModel::updateData,
        onClickFillProfile = onClickFillProfile,
        onClickMainPage = onClickMainPage,
        skip = skip
    )

    BackHandler {
        onClickMainPage()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegistrationScreen(
    modifier: Modifier = Modifier,
    onClickLogin: () -> Unit,
    state: RegistrationState,
    onEvent: (event: RegistrationEvent) -> Unit,
    onClickFillProfile: () -> Unit,
    onClickMainPage: () -> Unit,
    skip: () -> Unit
) {

    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    var passwordConfirmVisibility by rememberSaveable {
        mutableStateOf(false)
    }

    val icon = if (passwordVisibility) {
        painterResource(id = R.drawable.baseline_visibility_off_24)
    } else {
        painterResource(id = R.drawable.baseline_visibility_24)
    }

    val iconConfirmPassword = if (passwordConfirmVisibility) {
        painterResource(id = R.drawable.baseline_visibility_off_24)
    } else {
        painterResource(id = R.drawable.baseline_visibility_24)
    }

    when (state.uiState) {
        UIState.REGISTRATION -> {
            LoadingScreen()
        }

        UIState.SUCCESS_REGISTRATION -> {
            onClickFillProfile()
        }

        UIState.DATA_ENTRY -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "decide",
                        style = DecideTheme.typography.displayLarge,
                        color = DecideTheme.colors.text
                    )

                    Text(
                        text = "Давай начнем",
                        style = DecideTheme.typography.displaySmall,
                        color = DecideTheme.colors.text
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Создайте учетную запись, чтобы \n" + "сохранить прогресс",
                        style = DecideTheme.typography.titleSmall,
                        color = DecideTheme.colors.gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    EditTextField(value = state.email,
                        onValueChange = { onEvent(RegistrationEvent.SetEmail(it)) },
                        labelText = "email",
                        isError = state.isErrorEmail.isError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        supportingText = state.isErrorEmail.nameError,
                        isFocus = {})

                    EditTextField(value = state.password,
                        onValueChange = { onEvent(RegistrationEvent.SetPassword(it)) },
                        labelText = "Пароль",
                        trailingIcon = {
                            if (state.password.isNotBlank()) IconButton(onClick = {
                                passwordVisibility = !passwordVisibility
                            }) {
                                Icon(
                                    painter = icon,
                                    contentDescription = "Visibility",
                                    tint = DecideTheme.colors.gray
                                )
                            }
                        },
                        supportingText = state.isErrorPassword.nameError,
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        isFocus = {})

                    EditTextField(value = state.confirmPassword,
                        onValueChange = { onEvent(RegistrationEvent.SetConfirmPassword(it)) },
                        labelText = "Подтвердите пароль",
                        trailingIcon = {
                            if (state.confirmPassword.isNotBlank()) IconButton(onClick = {
                                passwordConfirmVisibility = !passwordConfirmVisibility
                            }) {
                                Icon(
                                    painter = iconConfirmPassword,
                                    contentDescription = "Visibility",
                                    tint = DecideTheme.colors.gray
                                )
                            }
                        },
                        isError = state.isErrorPasswordConfirm.isError,
                        supportingText = state.isErrorPassword.nameError,
                        visualTransformation = if (passwordConfirmVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        isFocus = {})

                    Spacer(modifier = Modifier.height(16.dp))
                    ButtonEntry(text = "Регистрация") {
                        onEvent(RegistrationEvent.TryRegistration)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ButtonEntry(
                        text = "Пропустить",
                        background = DecideTheme.colors.unFocused40,
                        textColor = DecideTheme.colors.white
                    ) {
                        skip()
                    }

                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 26.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "или с помощью",
                        style = DecideTheme.typography.titleSmall,
                        color = DecideTheme.colors.unFocused
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    IconButton(onClick = {
                        onEvent(RegistrationEvent.AuthWithVK)
                    }) {
                        Icon(
                            painter = painterResource(com.decide.uikit.R.drawable.ic_vk),
                            tint = null,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Уже есть профиль?",
                            style = DecideTheme.typography.titleSmall,
                            color = DecideTheme.colors.text
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .clickable { onClickLogin() },
                            text = "Вход",
                            color = DecideTheme.colors.accentPink,
                            style = DecideTheme.typography.titleSmall,
                        )
                    }
                }

            }
        }

        UIState.NO_INTERNET -> {
            NetworkErrorScreen {
                onEvent(RegistrationEvent.TryRegistration)
            }
        }

        UIState.UNKNOWN_ERROR -> {
            ErrorScreen {
                onClickMainPage()
            }
        }
    }
}


@MainPreview
@Composable
private fun PreviewRegistration() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            RegistrationScreen(modifier = Modifier,
                onClickLogin = {},
                onEvent = {},
                state = RegistrationState(),
                onClickFillProfile = {},
                onClickMainPage = {},
                skip = {})
        }
    }
}