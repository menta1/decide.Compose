package com.decide.app.feature.profile.profileMain.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
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
import com.decide.uikit.ui.dialog.SuccessDialog
import com.decide.uikit.ui.statistic.LineStatistic
import com.decide.uikit.ui.statistic.PieStatistic

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
    var show by remember { mutableStateOf(false) }

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
                },
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(110.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(start = 8.dp)
                        .basicMarquee(),
                    text = profileUI.firstName + " " + profileUI.lastName,
                    style = DecideTheme.typography.bodyLarge,
                    color = DecideTheme.colors.text
                )
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(start = 8.dp)
                        .basicMarquee(),
                    text = profileUI.email,
                    style = DecideTheme.typography.titleSmall,
                    color = DecideTheme.colors.gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "Cтатистика",
                style = DecideTheme.typography.titleLarge,
                color = DecideTheme.colors.text
            )

            IconButton(onClick = {
                show = true
            }) {
                Icon(
                    painter = painterResource(com.decide.app.R.drawable.ic_help_circle),
                    contentDescription = null,
                    tint = DecideTheme.colors.accentPink
                )
            }
        }
        if (show) {
            SuccessDialog(title = "Это общая статистика среди всех участников. Чтобы получать статистику нужно проходить больше тестов, так как результат считается исходя из результатов нескольких тестов",
                textStyle = DecideTheme.typography.bodyMedium,
                containerPadding = 8.dp,
                onDismissRequest = { show = false })
        }

        Spacer(modifier = Modifier.height(18.dp))

        ContentProfile(
            profileUI = profileUI
        )
    }
}

@Composable
private fun ContentProfile(
    profileUI: ProfileUI,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(DecideTheme.colors.container), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (profileUI.temperament == null) 0.4f else 1f),
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(start = 4.dp),
                    text = "Темперамент",
                    style = DecideTheme.typography.titleLarge,
                    color = DecideTheme.colors.text
                )
                if (!profileUI.dateTemperament.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .padding(start = 4.dp),
                        text = "от " + profileUI.dateTemperament,
                        style = DecideTheme.typography.titleSmall,
                        color = DecideTheme.colors.text
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            PieStatistic(
                data = profileUI.temperament
            )
        }

        if (profileUI.temperament == null) {
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

    Statistic(
        data = profileUI.anxiety,
        nameStat = "Уровень тревожности",
        date = profileUI.dateAnxiety
    )
    Spacer(modifier = Modifier.height(18.dp))
    Statistic(
        data = profileUI.depression,
        nameStat = "Уровень депрессии",
        date = profileUI.dateDepression
    )
}

@Composable
private fun Statistic(
    data: Pair<Float, Float>?,
    nameStat: String,
    date: String? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(DecideTheme.colors.container), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (data == null) 0.4f else 1f),
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(start = 4.dp),
                    text = nameStat,
                    style = DecideTheme.typography.titleLarge,
                    color = DecideTheme.colors.text
                )

                if (!date.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .padding(start = 4.dp),
                        text = "от $date",
                        style = DecideTheme.typography.titleSmall,
                        color = DecideTheme.colors.text
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            LineStatistic(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 4.dp),
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
                modifier = Modifier, onClickSetting = {}, profileUI = ProfileUI(
                    firstName = "AinurвввввввввввввввввввввввввввввввввввAinurввввввввввввввввввввввввввввввввввввввAinurввввввввввввввввввввввввввввввввввввввAinurввввввввввввввввввввввввввввввввввввввввв",
                    lastName = "a;sldajkdAinurввввввввввввввввввввввввввввввввввввввAinurввввввввввввввввввввввввввввввввввввввAinurввввввввввввввввввввввввввввввввввввввjaskd",
                    email = "asdasd@asdasAinurввввввввввввввввввввввввввввввввввввввAinurввввввввввввввввввввввввввввввввввввввAinurввввввввввввввввввввввввввввввввввввввAinurввввввввввввввввввввввввввввввввввввввd.eer",
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