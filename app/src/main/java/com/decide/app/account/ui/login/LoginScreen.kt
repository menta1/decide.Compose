package com.decide.app.account.ui.login

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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.R
import com.decide.app.account.ui.registration.RegistrationEvent
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ButtonEntry
import com.decide.uikit.ui.defaultScreens.ErrorScreen
import com.decide.uikit.ui.defaultScreens.LoadingScreen
import com.decide.uikit.ui.defaultScreens.NetworkErrorScreen
import com.decide.uikit.ui.text.EditTextField

@Composable
fun LoginScreen(
    onClickRegistration: () -> Unit,
    onAuth: () -> Unit,
    onClickMainPage: () -> Unit,
    onClickForgotPassword: () -> Unit
) {

    val viewModel: LoginScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginScreen(
        modifier = Modifier,
        state = state,
        onEvent = viewModel::onEvent,
        onClickRegistration = onClickRegistration,
        onAuth = onAuth,
        onClickMainPage = onClickMainPage,
        onClickForgotPassword = onClickForgotPassword
    )

    BackHandler {
        onClickMainPage()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginScreenState,
    onEvent: (event: LoginScreenEvent) -> Unit,
    onClickRegistration: () -> Unit,
    onAuth: () -> Unit,
    onClickMainPage: () -> Unit,
    onClickForgotPassword: () -> Unit
) {

    when (state.uiState) {
        UIState.DATA_ENTRY -> {
            var passwordVisibility by rememberSaveable {
                mutableStateOf(false)
            }
            val icon = if (passwordVisibility) {
                painterResource(id = R.drawable.baseline_visibility_off_24)
            } else {
                painterResource(id = R.drawable.baseline_visibility_24)
            }

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
                        text = "Давайте войдем в систему",
                        style = DecideTheme.typography.displayMedium,
                        color = DecideTheme.colors.text
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Войдите в свою учетную запись",
                        style = DecideTheme.typography.labelLarge,
                        color = DecideTheme.colors.gray
                    )

                    if (state.exceptionAuth) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Неверный email или пароль",
                            style = DecideTheme.typography.titleMedium,
                            color = DecideTheme.colors.error
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    EditTextField(
                        value = state.email,
                        onValueChange = { onEvent(LoginScreenEvent.SetEmail(it)) },
                        labelText = "Email",
                        isError = state.isErrorEmail.isError,
                        supportingText = state.isErrorEmail.nameError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isFocus = {})

                    EditTextField(
                        value = state.password,
                        onValueChange = { onEvent(LoginScreenEvent.SetPassword(it)) },
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
                        isError = state.isErrorPassword.isError,
                        supportingText = state.isErrorPassword.nameError,
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        isFocus = {})

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            modifier = Modifier
                                .clickable {
                                    onClickForgotPassword()
                                },
                            style = DecideTheme.typography.titleSmall,
                            color = DecideTheme.colors.text,
                            text = "Забыли пароль?"
                        )
                    }


                    Spacer(modifier = Modifier.height(16.dp))
                    ButtonEntry(text = "Вход") {
                        onEvent(LoginScreenEvent.TryAuth)
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
                        onEvent(LoginScreenEvent.AuthWithVK)
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
                            text = "У вас нет профиля?",
                            style = DecideTheme.typography.titleSmall,
                            color = DecideTheme.colors.text
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .clickable { onClickRegistration() },
                            text = "Регистрация",
                            style = DecideTheme.typography.titleSmall,
                            color = DecideTheme.colors.accentPink
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "продолжая Вы соглашайтесь с ",
                            style = DecideTheme.typography.labelSmall,
                            color = DecideTheme.colors.gray
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .clickable { onEvent(LoginScreenEvent.PrivacyPolicy) },
                            text = "политикой конфиденциальности",
                            color = DecideTheme.colors.gray,
                            style = DecideTheme.typography.labelSmall,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }

            }
        }

        UIState.PROCESS_AUTH -> LoadingScreen()

        UIState.SUCCESS_AUTH -> {
            onAuth()
        }

        UIState.ERROR -> ErrorScreen { }

        UIState.NETWORK_ERROR -> {
            NetworkErrorScreen {
                onEvent(LoginScreenEvent.TryAuth)
            }
        }
    }

}

//@Composable
//fun ScreenWithVKIDButton() {
//    OneTap(
//        onAuth = {  }
//    )
//}


@MainPreview
@Composable
fun PreviewLogin() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            LoginScreen(modifier = Modifier,
                state = LoginScreenState(),
                onEvent = {},
                onClickRegistration = {},
                onAuth = {},
                onClickMainPage = {},
                onClickForgotPassword = {})
        }
    }
}