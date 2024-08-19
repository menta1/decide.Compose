package com.decide.app.feature.assay.assayDescription.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ButtonBackArrow
import com.decide.uikit.ui.buttons.ButtonMain

@Composable
fun AssayDescriptionScreen(
    modifier: Modifier = Modifier,
    idAssay: Int?,
    viewModel: AssayDescriptionViewModel = hiltViewModel(),
    onClickStart: (argument: Int) -> Unit,
    onClickBack: () -> Unit
) {
    viewModel.getId(idAssay ?: -1)
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
            .background(DecideTheme.colors.mainBlue),
    ) {
        ButtonBackArrow(text = "Назад", onClick = { onClickBack() })

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 8.dp)
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    modifier = Modifier.fillMaxHeight(),
                    text =
                    when (state) {
                        AssayDescriptionState.Default -> "Пустое описание"
                        is AssayDescriptionState.Success -> (state as AssayDescriptionState.Success).data.description
                        AssayDescriptionState.Error -> {
                            "Ничего не найдено"
                        }
                    },
                    style = DecideTheme.typography.searchText,
                    color = DecideTheme.colors.inputBlack,
                )

            }
            ButtonMain(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
                text = "Начать"
            ) {
                onClickStart(idAssay ?: -1)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAssayDescriptionScreen() {
    AssayDescriptionScreen(idAssay = null, onClickBack = {}, onClickStart = {})
}