package com.decide.app.feature.assay.assayResult.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ButtonMain

@Composable
fun AssayWithResultScreen(
    modifier: Modifier = Modifier,
    onClickExit: () -> Unit
) {

    val viewModel: AssayWithResultViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    AssayWithResultScreen(
        modifier = modifier,
        onClickExit = onClickExit,
        state = state
    )
}


@Composable
fun AssayWithResultScreen(
    modifier: Modifier = Modifier,
    onClickExit: () -> Unit,
    state: AssayWithResultState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DecideTheme.colors.mainBlue)
            .padding(top = 36.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            AssayWithResultState.Default -> {}
            AssayWithResultState.Error -> {}
            is AssayWithResultState.Loaded -> {
                Text(
                    text = state.result,
                    style = DecideTheme.typography.searchText,
                    color = DecideTheme.colors.inputBlack,

                    )
            }

            AssayWithResultState.Loading -> {
                Text(
                    modifier = Modifier.padding(top = 60.dp),
                    text = "Анализируем...",
                    style = DecideTheme.typography.searchText,
                    color = DecideTheme.colors.inputBlack,

                    )
                CircularProgressIndicator(
                    color = DecideTheme.colors.accentYellow
                )
            }
        }

        ButtonMain(
            modifier = Modifier
                .padding(bottom = 10.dp),
            text = "Выход",
        ) {
            onClickExit()
        }

    }
}

/**
 * AssayWithResultState.Loaded
 */
@Preview
@Composable
fun PreviewAssayWithResultScreenLoaded() {
    val state: AssayWithResultState by remember {
        mutableStateOf(AssayWithResultState.Loaded("Очень высокий уровень фрустрированности указывает на наличие серьезных эмоциональных проблем и неудовлетворенности, которые сильно влияют на качество жизни. Из этого может вытекать сильное напряжение, депрессия, агрессия, проблемы в отношениях, а также ухудшение физического и психического здоровья. Обсуждение результатов теста с опытным специалистом и поиск подходящей поддержки могут помочь разобраться в источнике фрустрации и разработать стратегии по ее преодолению."))
    }
    DecideTheme {
        Column {
            AssayWithResultScreen(state = state, onClickExit = {})
        }
    }
}

/**
 * AssayWithResultState.Loaded
 */
@Preview
@Composable
fun PreviewAssayWithResultScreenLoading() {
    val state: AssayWithResultState by remember {
        mutableStateOf(AssayWithResultState.Loading)
    }
    DecideTheme {
        Column {
            AssayWithResultScreen(state = state, onClickExit = {})
        }
    }
}