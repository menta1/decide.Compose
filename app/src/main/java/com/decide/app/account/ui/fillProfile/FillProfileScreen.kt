package com.decide.app.account.ui.fillProfile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.decide.app.R
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.EditTextField
import com.decide.uikit.ui.ErrorMessage
import com.decide.uikit.ui.buttons.ButtonBackArrow
import com.decide.uikit.ui.buttons.ButtonEntry
import com.decide.uikit.ui.buttons.ButtonMain
import com.decide.uikit.ui.buttons.CircleDecideIndicator

@Composable
fun FillProfileScreen(
    onClickBack: () -> Unit,
    onClickContinue: () -> Unit,
    onClickMainPage: () -> Unit
) {
    val viewModel: FillProfileViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    FillProfileScreen(
        modifier = Modifier,
        state = state,
        onEvent = viewModel::onEvent,
        onClickBack = onClickBack,
        onClickContinue = onClickContinue,
        onClickMainPage = onClickMainPage
    )
}

@Composable
fun FillProfileScreen(
    modifier: Modifier,
    state: FillProfileState,
    onEvent: (event: FillProfileEvent) -> Unit,
    onClickBack: () -> Unit,
    onClickContinue: () -> Unit,
    onClickMainPage: () -> Unit
) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                onEvent(FillProfileEvent.SetUriAvatar(it))
                imageUri = it
            }
        }
    )

    when (state.uiState) {
        UIState.DATA_ENTRY -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp)
                    .padding(top = 8.dp),
            ) {
                ButtonBackArrow(
                    text = "Заполните свой профиль",
                    onClick = { onClickBack() })

                Spacer(modifier = Modifier.height(44.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.clickable {
                            galleryLauncher.launch("image/*")
                        }
                    ) {
                        Image(
                            painter = if (imageUri == null) {
                                painterResource(id = R.drawable.profile)
                            } else {
                                rememberAsyncImagePainter(
                                    model = imageUri
                                )
                            },
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(84.dp)
                        )
                    }

                    EditTextField(
                        value = state.firstName,
                        onValueChange = { onEvent(FillProfileEvent.SetFirstName(it)) },
                        supportingText = if (state.isErrorFirstName) "Это поле не может быть пустым!" else "",
                        isError = state.isErrorFirstName,
                        labelText = "Имя*",
                        isFocus = {}
                    )
                    EditTextField(
                        value = state.secondName,
                        onValueChange = { onEvent(FillProfileEvent.SetSecondName(it)) },
                        supportingText = "",
                        labelText = "Фамилия",
                        isFocus = {}
                    )
                }

                ButtonEntry(text = "Продолжить") {
                    onEvent(FillProfileEvent.Continue)
                }

            }
        }

        UIState.LOADING -> {
            Loading()
        }

        UIState.SUCCESS -> {
            onClickContinue()
        }

        UIState.ERROR -> {
            Error(
                onClickMainPage = onClickMainPage
            )
        }
    }
}

@Composable
internal fun Error(
    onClickMainPage: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        ErrorMessage()
        Spacer(modifier = Modifier.height(32.dp))
        ButtonMain(
            modifier = Modifier.padding(horizontal = 14.dp),
            text = "Вернуться на главную страницу"
        ) {
            onClickMainPage()
        }


    }
}

@Composable
internal fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircleDecideIndicator()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFillProfileScreen() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            FillProfileScreen(
                modifier = Modifier,
                state = FillProfileState(),
                onEvent = {},
                onClickBack = { },
                {}, {}
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewError() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            FillProfileScreen(
                modifier = Modifier,
                state = FillProfileState(uiState = UIState.ERROR),
                onEvent = {},
                onClickBack = { },
                {}, {}
            )
        }
    }
}

