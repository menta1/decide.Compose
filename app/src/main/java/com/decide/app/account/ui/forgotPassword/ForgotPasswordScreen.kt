package com.decide.app.account.ui.forgotPassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.ErrorMessage
import com.decide.uikit.ui.buttons.ButtonBackArrow
import com.decide.uikit.ui.buttons.ButtonEntry
import com.decide.uikit.ui.buttons.CircleDecideIndicator
import com.decide.uikit.ui.defaultScreens.NetworkErrorScreen
import com.decide.uikit.ui.text.EditTextField

@Composable
fun ForgotPasswordScreen(
    onClickBack: () -> Unit
) {
    val viewModel: ForgotPasswordViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ForgotPasswordScreen(
        onEvent = viewModel::onEvent,
        state = state,
        onClickBack = onClickBack
    )
}

@Composable
fun ForgotPasswordScreen(
    onEvent: (ForgotPasswordEvent) -> Unit,
    state: ForgotPasswordState,
    onClickBack: () -> Unit
) {
    when (state.uiState) {
        UIState.LOADING -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircleDecideIndicator()
            }
        }

        UIState.SUCCESS -> {
            onClickBack()
        }

        UIState.DATA_ENTRY -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 14.dp)
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ButtonBackArrow(
                    text = "Забыли пароль",
                    onClick = { onClickBack() })

                Column {
                    Spacer(modifier = Modifier.height(42.dp))

                    Text(
                        text = "Введите Вашу почту для восстановления",
                        style = DecideTheme.typography.headlineSmall,
                        color = DecideTheme.colors.text
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    EditTextField(
                        value = state.email,
                        onValueChange = { onEvent(ForgotPasswordEvent.SetEmail(it)) },
                        labelText = "Пароль",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = state.isErrorEmail.isError,
                        supportingText = state.isErrorEmail.nameError,
                        isFocus = {})

                }


                ButtonEntry(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "Отправить"
                ) {
                    onEvent(ForgotPasswordEvent.SendToEmail)
                }

            }
        }

        UIState.NO_INTERNET -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                NetworkErrorScreen(
                    onClick = {}
                )
            }
        }

        UIState.UNKNOWN_ERROR -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ErrorMessage()
            }
        }
    }

}

@MainPreview
@Composable
fun PreviewForgotPasswordScreen() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            ForgotPasswordScreen(
                onEvent = {},
                state = ForgotPasswordState(),
                onClickBack = {}
            )
        }
    }
}