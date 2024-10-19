package com.decide.app.feature.profile.settings.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.ErrorMessage
import com.decide.uikit.ui.buttons.CircleDecideIndicator
import com.decide.uikit.ui.buttons.ItemSettings

@Composable
fun SettingsScreen(
    onClickEditProfile: () -> Unit,
    onClickLogOut: () -> Unit,
    onClickTerms: () -> Unit,
    onClickNotifications: () -> Unit,
) {
    val viewModel: SettingsScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        modifier = Modifier,
        onClickEditProfile = onClickEditProfile,
        onClickTerms = onClickTerms,
        onClickNotifications = onClickNotifications,
        onClickLogOut = onClickLogOut,
        onEvent = viewModel::onEvent,
        state = state
    )
}

@Composable
fun SettingsScreen(
    modifier: Modifier,
    onClickEditProfile: () -> Unit,
    onClickTerms: () -> Unit,
    onClickNotifications: () -> Unit,
    onClickLogOut: () -> Unit,
    onEvent: (event: SettingsScreenEvent) -> Unit,
    state: SettingsScreenState
) {

    when (state) {
        SettingsScreenState.Loaded -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp)
            ) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Настройки",
                    style = DecideTheme.typography.titleLarge,
                    color = DecideTheme.colors.inputBlack
                )

                Spacer(modifier = Modifier.height(16.dp))
                ItemSettings(
                    text = "Редактировать профиль",
                    iconItem = painterResource(id = R.drawable.ic_edit_profile)
                ) {
                    onClickEditProfile()
                }
                Spacer(modifier = Modifier.height(8.dp))
                ItemSettings(
                    text = "Уведомления",
                    iconItem = painterResource(id = R.drawable.ic_notifications)
                ) {
                    onClickNotifications()
                }

                Spacer(modifier = Modifier.height(8.dp))
                ItemSettings(
                    text = "Поделиться", iconItem = painterResource(id = R.drawable.ic_share)
                ) {
                    onClickTerms()
                }
                Spacer(modifier = Modifier.height(8.dp))

                ItemSettings(
                    text = "Написать нам", iconItem = painterResource(id = R.drawable.ic_write_us)
                ) {
                    onEvent(SettingsScreenEvent.SendEmail)
                }
                Spacer(modifier = Modifier.height(8.dp))

                ItemSettings(
                    text = "Политика конфиденциальности",
                    iconItem = painterResource(id = R.drawable.ic_privacy)
                ) {
                    onEvent(SettingsScreenEvent.PrivacyPolicy)
                }
                Spacer(modifier = Modifier.height(8.dp))
                ItemSettings(
                    text = "Выйти из профиля",
                    iconItem = painterResource(id = R.drawable.ic_log_out)
                ) {
                    onEvent(SettingsScreenEvent.LogOut)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = ripple(
                                radius = 0.dp,
                                color = DecideTheme.colors.inputBlack
                            ),
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {

                            }),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Удалить аккаунт",
                        style = DecideTheme.typography.bodySmall,
                        color = DecideTheme.colors.error,
                        textAlign = TextAlign.Center
                    )
                }

            }
        }

        SettingsScreenState.Loading -> {
            Loading()
        }

        is SettingsScreenState.Error -> {
            Error()
        }

        SettingsScreenState.LogOut -> {
            onClickLogOut()
        }
    }


}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewLoaded() {
    DecideTheme {
        Column(modifier = Modifier.background(Color.Black)) {
            SettingsScreen(
                modifier = Modifier,
                onClickEditProfile = {},
                onClickTerms = {},
                onClickNotifications = {},
                onClickLogOut = {},
                onEvent = {},
                state = SettingsScreenState.Loaded
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Error() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ErrorMessage()
    }
}

@Preview(showBackground = true)
@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircleDecideIndicator()
    }
}


