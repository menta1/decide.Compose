package com.decide.app.feature.profile.settings.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.R
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
                ItemSettings(
                    text = "Уведомления",
                    iconItem = painterResource(id = R.drawable.ic_notifications)
                ) {
                    onClickNotifications()
                }
                ItemSettings(
                    text = "Условия", iconItem = painterResource(id = R.drawable.ic_terms)
                ) {
                    onClickTerms()
                }
                ItemSettings(
                    text = "Выйти из профиля",
                    iconItem = painterResource(id = R.drawable.ic_log_out)
                ) {
                    onEvent(SettingsScreenEvent.LogOut)
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
        Column {
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


