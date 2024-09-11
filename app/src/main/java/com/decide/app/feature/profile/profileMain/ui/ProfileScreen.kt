package com.decide.app.feature.profile.profileMain.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.decide.app.R
import com.decide.app.feature.profile.profileMain.modal.ProfileUI
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.ErrorMessage
import com.decide.uikit.ui.buttons.ButtonEntry
import com.decide.uikit.ui.buttons.CircleDecideIndicator
import com.decide.uikit.ui.statistic.LineStatistic
import com.decide.uikit.ui.statistic.PieStatistic

@Composable
fun ProfileScreen(
    onClickSetting: () -> Unit,
    onClickLogin: () -> Unit,
    onClickRegistration: () -> Unit,
) {

    val viewModel: ProfileViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(ProfileScreenEvent.UpdateProfile)
    }

    ProfileScreen(
        state = state,
        onClickSetting = onClickSetting,
        onClickLogin = onClickLogin,
        onClickRegistration = onClickRegistration
    )
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileState,
    onClickSetting: () -> Unit,
    onClickLogin: () -> Unit,
    onClickRegistration: () -> Unit,
) {

    when (state) {
        ProfileState.Empty -> {}
        is ProfileState.Error -> Error()
        ProfileState.Loading -> Loading()
        ProfileState.NotAuthorized -> {
            NotAuthorized(
                modifier = modifier,
                onClickLogin = onClickLogin,
                onClickRegistration = onClickRegistration
            )
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
fun Loaded(
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
                color = DecideTheme.colors.inputBlack
            )

            IconButton(onClick = {
                onClickSetting()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "button settings",
                    tint = DecideTheme.colors.inputBlack
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
            Text(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .padding(start = 8.dp),
                text = profileUI.firstName + " " + profileUI.lastName,
                style = DecideTheme.typography.titleLarge,
                color = DecideTheme.colors.inputBlack
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(start = 8.dp),
            text = "Ваша статистика",
            style = DecideTheme.typography.titleLarge,
            color = DecideTheme.colors.inputBlack
        )

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier
                .padding(top = 4.dp),
            text = "Темперамент",
            style = DecideTheme.typography.titleMedium,
            color = DecideTheme.colors.inputBlack
        )
        Spacer(modifier = Modifier.height(8.dp))
        ContentProfile(
            anxiety = profileUI.anxiety
        )

    }
}

@Composable
fun ContentProfile(
    anxiety: Pair<Float, Float>?,
) {
    PieStatistic(
        delayTime = 500,
        data = mapOf(
            "Холерик" to 25,
            "Сангвиник" to 25,
            "Флегматик" to 25,
            "Меланхолик" to 25,
        )
    )
    Spacer(modifier = Modifier.height(32.dp))
    Text(
        modifier = Modifier
            .padding(top = 4.dp),
        text = "Уровень тревожности",
        style = DecideTheme.typography.titleMedium,
        color = DecideTheme.colors.inputBlack
    )
    Spacer(modifier = Modifier.height(8.dp))
    if (anxiety != null) {
        LineStatistic(
            your = anxiety.first,
            all = anxiety.second,
            isHasResult = true,
            modifier = Modifier
                .clickable { },
            delayTime = 1000
        )
    }

}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewLoaded() {
    DecideTheme {
        Column {
            ProfileState.Loaded(
                profileUI = ProfileUI(
                    firstName = "Ainur",
                    lastName = "a;sldajkdjaskd",
                    email = "asdasd@asdasd.eer",
                    anxiety = Pair(34f, 66f)
                )
            )
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NotAuthorized(
    modifier: Modifier = Modifier,
    onClickLogin: () -> Unit = {},
    onClickRegistration: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Станет доступна статистика и психологический портрет",
            textAlign = TextAlign.Center,
            style = DecideTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(84.dp))


        ButtonEntry(text = "Войти в профиль") {
            onClickLogin()
        }

        Spacer(modifier = Modifier.height(36.dp))

        ButtonEntry(
            text = "Регистрация"
        ) {
            onClickRegistration()
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
