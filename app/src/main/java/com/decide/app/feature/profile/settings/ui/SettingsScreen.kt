package com.decide.app.feature.profile.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.uikit.R
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ItemSettings
import com.decide.uikit.ui.defaultScreens.ErrorScreen
import com.decide.uikit.ui.defaultScreens.LoadingScreen

@Composable
fun SettingsScreen(
    onClickEditProfile: () -> Unit,
    onClickLogOut: () -> Unit,
    onClickNotifications: () -> Unit,
) {
    val viewModel: SettingsScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        modifier = Modifier,
        onClickEditProfile = onClickEditProfile,
        onClickNotifications = onClickNotifications,
        onClickLogOut = onClickLogOut,
        onEvent = viewModel::onEvent,
        state = state
    )
}

@Composable
private fun SettingsScreen(
    modifier: Modifier,
    onClickEditProfile: () -> Unit,
    onClickNotifications: () -> Unit,
    onClickLogOut: () -> Unit,
    onEvent: (event: SettingsScreenEvent) -> Unit,
    state: SettingsScreenState
) {

    when (state.uiState) {
        SettingState.Loaded -> {
            Loaded(
                modifier = modifier,
                onClickEditProfile = onClickEditProfile,
                onClickNotifications = onClickNotifications,
                onEvent = onEvent,
                state = state
            )
        }

        SettingState.Loading -> LoadingScreen()

        SettingState.Error -> ErrorScreen { }


        SettingState.LogOut -> {
            onClickLogOut()
        }

    }

}

@Composable
private fun Loaded(
    modifier: Modifier,
    onClickEditProfile: () -> Unit,
    onClickNotifications: () -> Unit,
    onEvent: (event: SettingsScreenEvent) -> Unit,
    state: SettingsScreenState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "Настройки",
            style = DecideTheme.typography.titleLarge,
            color = DecideTheme.colors.text
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
            text = "Уведомления", iconItem = painterResource(id = R.drawable.ic_notifications)
        ) {
            onClickNotifications()
        }

        Spacer(modifier = Modifier.height(8.dp))
        ItemSettings(
            text = "Поделиться", iconItem = painterResource(id = R.drawable.ic_share)
        ) {

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
            text = "Выйти из профиля", iconItem = painterResource(id = R.drawable.ic_log_out)
        ) {
            onEvent(SettingsScreenEvent.LogOut)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {

                    }), contentAlignment = Alignment.Center
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

@MainPreview
@Composable
private fun Preview() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            SettingsScreen(
                modifier = Modifier,
                onClickEditProfile = {},
                onClickNotifications = {},
                onClickLogOut = {},
                onEvent = {},
                state = SettingsScreenState()
            )
        }
    }
}