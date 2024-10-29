package com.decide.app.feature.profile.profileMain.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.decide.app.feature.profile.profileMain.modal.ProfileUI
import com.decide.uikit.R
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.defaultScreens.ErrorScreen
import com.decide.uikit.ui.defaultScreens.LoadingScreen
import com.decide.uikit.ui.statistic.LineStatistic
import com.decide.uikit.ui.statistic.PieStatistic
import com.decide.uikit.ui.statistic.modal.TemperamentUI

@Composable
fun ProfileScreen(
    onClickSetting: () -> Unit,
    onClickLogin: () -> Unit,
) {

    val viewModel: ProfileViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProfileScreen(
        state = state,
        onClickSetting = onClickSetting,
        onClickLogin = onClickLogin,
    )
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileState,
    onClickSetting: () -> Unit,
    onClickLogin: () -> Unit,
) {
    when (state) {
        ProfileState.Empty -> {}
        is ProfileState.Error -> ErrorScreen { }
        ProfileState.Loading -> LoadingScreen()
        ProfileState.NotAuthorized -> {
            onClickLogin()
        }

        is ProfileState.Loaded -> {
            Loaded(
                modifier = modifier,
                onClickSetting = onClickSetting,
                profileUI = state.profileUI,
            )
        }
    }

}


@Composable
private fun Loaded(
    modifier: Modifier = Modifier,
    onClickSetting: () -> Unit = {},
    profileUI: ProfileUI,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 14.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Профиль",
                style = DecideTheme.typography.titleLarge,
                color = DecideTheme.colors.text
            )

            IconButton(onClick = {
                onClickSetting()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "button settings",
                    tint = DecideTheme.colors.text
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Image(
                painter = if (profileUI.avatar == null) {
                    painterResource(id = R.drawable.placeholder_fill_profile)
                } else {
                    rememberAsyncImagePainter(
                        model = profileUI.avatar
                    )
                }, contentDescription = null, modifier = Modifier
                    .clip(CircleShape)
                    .size(110.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(start = 8.dp),
                    text = profileUI.firstName + " " + profileUI.lastName,
                    style = DecideTheme.typography.titleLarge,
                    color = DecideTheme.colors.text
                )
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(start = 8.dp),
                    text = profileUI.email,
                    style = DecideTheme.typography.titleMedium,
                    color = DecideTheme.colors.gray
                )
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .padding(top = 4.dp),
            text = "Ваша статистика",
            style = DecideTheme.typography.titleLarge,
            color = DecideTheme.colors.text
        )

        Spacer(modifier = Modifier.height(18.dp))

        ContentProfile(
            anxiety = profileUI.anxiety,
            depression = profileUI.depression,
            temperament = profileUI.temperament
        )

    }
}

@Composable
private fun ContentProfile(
    anxiety: Pair<Float, Float>?,
    depression: Pair<Float, Float>?,
    temperament: TemperamentUI?
) {

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (temperament == null) 0.4f else 1f),
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = "Темперамент",
                style = DecideTheme.typography.titleMedium,
                color = DecideTheme.colors.text
            )
            Spacer(modifier = Modifier.height(8.dp))

            PieStatistic(
                data = temperament
            )
        }

        if (temperament == null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Данные не актуальны",
                    style = DecideTheme.typography.titleLarge,
                    color = DecideTheme.colors.text
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "проходите больше тестов",
                    style = DecideTheme.typography.titleLarge,
                    color = DecideTheme.colors.text
                )
            }
        }

    }

    Spacer(modifier = Modifier.height(32.dp))

    Statistic(data = anxiety, nameStat = "Уровень тревожности")
    Spacer(modifier = Modifier.height(18.dp))
    Statistic(data = depression, nameStat = "Уровень депрессии")

}

@Composable
private fun Statistic(
    data: Pair<Float, Float>?,
    nameStat: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (data == null) 0.4f else 1f),
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = nameStat,
                style = DecideTheme.typography.titleMedium,
                color = DecideTheme.colors.text
            )
            Spacer(modifier = Modifier.height(12.dp))
            LineStatistic(
                modifier = Modifier.padding(horizontal = 8.dp),
                your = data?.first ?: 80f,
                all = data?.second ?: 20f,
                isHasResult = true,
                delayTime = 1000
            )
        }

        if (data == null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Данные не актуальны",
                    style = DecideTheme.typography.titleLarge,
                    color = DecideTheme.colors.text
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "проходите больше тестов",
                    style = DecideTheme.typography.titleLarge,
                    color = DecideTheme.colors.text
                )
            }
        }
    }
}

@MainPreview
@Composable
private fun Preview() {
    DecideTheme {
        Column {
            Loaded(
                modifier = Modifier,
                onClickSetting = {},
                profileUI = ProfileUI(
                    firstName = "Ainur",
                    lastName = "a;sldajkdjaskd",
                    email = "asdasd@asdasd.eer",
                    anxiety = null,
//                    Pair(34f, 66f),
                    depression = null,
                    temperament = null
//                    TemperamentUI(
//                        choleric = Pair(first = "Холерик", second = 1.0),
//                        sanguine = Pair(first = "Сангвиник", second = 1.0),
//                        melancholic = Pair(first = "Меланхолик", second = 1.0),
//                        phlegmatic = Pair(first = "Флегматик", second = 1.0)
//                    ),
                )
            )
        }
    }
}