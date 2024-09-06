package com.decide.app.account.ui.registration

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.R
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ButtonEntry
import com.decide.uikit.ui.buttons.CircleDecideIndicator

@Composable
fun RegistrationScreen(
    onClickFillProfile: () -> Unit,
    onClickBack: () -> Unit,
    onClickLogin: () -> Unit,
    onClickMainPage: () -> Unit
) {

    val viewModel: RegistrationViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    RegistrationScreen(
        modifier = Modifier,
        onClickLogin = onClickLogin,
        state = state,
        onEvent = viewModel::updateData,
        onClickFillProfile = onClickFillProfile
    )
}

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    onClickLogin: () -> Unit,
    state: RegistrationState,
    onEvent: (event: RegistrationEvent) -> Unit,
    onClickFillProfile: () -> Unit,
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
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircleDecideIndicator()
            }
        }

        UIState.SUCCESS_REGISTRATION -> {
            onClickFillProfile()
        }

        UIState.DATA_ENTRY -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier.padding(24.dp),
                    text = "decide",
                    style = DecideTheme.typography.displayLarge,
                    color = DecideTheme.colors.inputBlack
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Давай начнем",
                        style = DecideTheme.typography.displaySmall,
                        color = DecideTheme.colors.inputBlack
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Создайте учетную запись, чтобы \n" + "сохранить прогресс",
                        style = DecideTheme.typography.titleSmall,
                        color = DecideTheme.colors.gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = state.email,
                        onValueChange = { onEvent(RegistrationEvent.SetEmail(it)) },
                        maxLines = 1,
                        isError = state.isErrorEmail.isError,
                        supportingText = {
                            Text(
                                text = state.isErrorEmail.nameError,
                                style = DecideTheme.typography.titleSmall,
                                color = DecideTheme.colors.error
                            )
                        },
                        label = {
                            Text(
                                text = "email", style = DecideTheme.typography.titleSmall
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DecideTheme.colors.inputBlack,
                            focusedLabelColor = DecideTheme.colors.inputBlack,
                            unfocusedLabelColor = DecideTheme.colors.gray
                        )
                    )
                    OutlinedTextField(modifier = Modifier
                        .fillMaxWidth(),
                        value = state.password,
                        onValueChange = { onEvent(RegistrationEvent.SetPassword(it)) },
                        maxLines = 1,
                        label = {
                            Text(
                                text = "Пароль", style = DecideTheme.typography.titleSmall
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            if (state.password.isNotBlank()) IconButton(onClick = {
                                passwordVisibility = !passwordVisibility
                            }) {
                                Icon(painter = icon, contentDescription = "Visibility")
                            }
                        },
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DecideTheme.colors.inputBlack,
                            focusedLabelColor = DecideTheme.colors.inputBlack,
                            unfocusedLabelColor = DecideTheme.colors.gray,
                            focusedPlaceholderColor = DecideTheme.colors.gray,
                        ),
                        isError = state.isErrorPassword.isError,
                        supportingText = {
                            Text(
                                text = state.isErrorPassword.nameError,
                                style = DecideTheme.typography.titleSmall,
                                color = DecideTheme.colors.error
                            )
                        })
                    OutlinedTextField(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                        value = state.confirmPassword,
                        onValueChange = { onEvent(RegistrationEvent.SetConfirmPassword(it)) },
                        maxLines = 1,
                        label = {
                            Text(
                                text = "Подтвердите пароль",
                                style = DecideTheme.typography.titleSmall
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            if (state.confirmPassword.isNotBlank()) IconButton(onClick = {
                                passwordConfirmVisibility = !passwordConfirmVisibility
                            }) {
                                Icon(
                                    painter = iconConfirmPassword, contentDescription = "Visibility"
                                )
                            }
                        },
                        visualTransformation = if (passwordConfirmVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DecideTheme.colors.inputBlack,
                            focusedLabelColor = DecideTheme.colors.inputBlack,
                            unfocusedLabelColor = DecideTheme.colors.gray,
                            focusedPlaceholderColor = DecideTheme.colors.gray,
                        ),
                        isError = state.isErrorPasswordConfirm.isError,
                        supportingText = {
                            Text(
                                text = state.isErrorPasswordConfirm.nameError,
                                style = DecideTheme.typography.titleSmall,
                                color = DecideTheme.colors.error
                            )
                        })

                    Spacer(modifier = Modifier.height(16.dp))
                    ButtonEntry(text = "Регистрация") {
                        onEvent(RegistrationEvent.TryRegistration)
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {

                    Text(
                        text = "продолжить с ",
                        style = DecideTheme.typography.labelLarge,
                        color = DecideTheme.colors.inputBlack
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Image(
                        painter = painterResource(id = R.drawable.social_media),
                        contentDescription = null
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Уже есть профиль?",
                            style = DecideTheme.typography.labelSmall,
                            color = DecideTheme.colors.inputBlack
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .clickable { onClickLogin() },
                            text = "Вход",
                            color = DecideTheme.colors.accentPink,
                            style = DecideTheme.typography.labelLarge,
                        )
                    }

                }
            }
        }

        UIState.NO_INTERNET -> TODO()
        UIState.UNKNOWN_ERROR -> TODO()
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRegistration() {

    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            RegistrationScreen(
                modifier = Modifier,
                onClickLogin = {},
                onEvent = {},
                state = RegistrationState(),
                onClickFillProfile = {}
            )
        }
    }
}