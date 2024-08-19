package com.decide.app.feature.passed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun PassedScreen(
    modifier: Modifier = Modifier,
    onClickResult: (id: Int, date: Long) -> Unit,
) {

    val viewModel: PassedScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    PassedScreen(
        modifier = modifier,
        onClickResult = onClickResult,
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun PassedScreen(
    modifier: Modifier = Modifier,
    onClickResult: (id: Int, date: Long) -> Unit,
    state: PassedScreenState,
    onEvent: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DecideTheme.colors.mainBlue)
            .padding(top = 8.dp)
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Завершенные",
            style = DecideTheme.typography.titleScreen,
            color = DecideTheme.colors.inputBlack
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is PassedScreenState.Success -> {
                    ExpandablePassedAssayList(list = state.assays,
                        onShowDetailResult = { result: Pair<Int, Long> ->
                            onClickResult(result.first, result.second)
                        })
                }

                PassedScreenState.Default -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Ничего нет",
                            style = DecideTheme.typography.defaultTextEmpty,
                            color = DecideTheme.colors.inputBlack
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Пройдите хотя бы один тест",
                            style = DecideTheme.typography.defaultTextEmpty,
                            color = DecideTheme.colors.inputBlack
                        )
                    }
                }


                is PassedScreenState.Error -> {

                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewPassedScreen() {
    val state by remember {
        mutableStateOf(PassedScreenState.Initial)
    }
    DecideTheme {
        Column {
            PassedScreen(onClickResult = { id, date -> }, state = state, onEvent = {})
        }
    }
}