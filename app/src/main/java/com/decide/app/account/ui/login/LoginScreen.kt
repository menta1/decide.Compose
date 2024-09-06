package com.decide.app.account.ui.login

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
import androidx.compose.foundation.text.KeyboardOptions
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
fun LoginScreen(
    onClickRegistration: () -> Unit,
    onAuth: () -> Unit,
    onClickMainPage: () -> Unit
) {

    val viewModel: LoginScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginScreen(
        modifier = Modifier,
        state = state,
        onEvent = viewModel::onEvent,
        onClickRegistration = onClickRegistration,
        onAuth = onAuth
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginScreenState,
    onEvent: (event: LoginScreenEvent) -> Unit,
    onClickRegistration: () -> Unit,
    onAuth: () -> Unit
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
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier.padding(44.dp),
                    text = "decide",
                    style = DecideTheme.typography.displayLarge,
                    color = DecideTheme.colors.inputBlack
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,

                    ) {
                    Text(
                        text = "Давайте войдем в систему",
                        style = DecideTheme.typography.displaySmall,
                        color = DecideTheme.colors.inputBlack
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Войдите в свою учетную запись",
                        style = DecideTheme.typography.titleSmall,
                        color = DecideTheme.colors.gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.email,
                        onValueChange = { onEvent(LoginScreenEvent.SetEmail(it)) },
                        maxLines = 1,
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
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.password,
                        onValueChange = { onEvent(LoginScreenEvent.SetPassword(it)) },
                        maxLines = 1,
                        label = {
                            Text(
                                text = "Пароль", style = DecideTheme.typography.titleSmall
                            )
                        },
                        placeholder = {
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
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ButtonEntry(text = "Вход") {
                        onEvent(LoginScreenEvent.TryAuth)
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(26.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        Text(text = "продолжить с ")
                        Spacer(modifier = Modifier.height(12.dp))
                        Image(
                            painter = painterResource(id = R.drawable.social_media),
                            contentDescription = null
                        )

                        Row {
                            Text(text = "У вас нет профиля?")
                            Text(
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .clickable { onClickRegistration() },
                                text = "Регистрация",
                                color = DecideTheme.colors.accentPink
                            )
                        }

                    }
                }

            }
        }

        UIState.PROCESS_AUTH -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircleDecideIndicator()
            }
        }

        UIState.SUCCESS_AUTH -> {
            onAuth()
        }

    }

}


@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            LoginScreen(modifier = Modifier,
                state = LoginScreenState(),
                onEvent = {},
                onClickRegistration = {},
                onAuth = {}
            )
        }
    }
}