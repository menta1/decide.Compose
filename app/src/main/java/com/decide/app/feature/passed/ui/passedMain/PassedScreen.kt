package com.decide.app.feature.passed.ui.passedMain

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
import com.decide.uikit.ui.ErrorMessage
import com.decide.uikit.ui.buttons.CircleDecideIndicator

@Composable
fun PassedScreen(
    onClickResult: (id: Int, date: Long) -> Unit,
) {

    val viewModel: PassedScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    PassedScreen(
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
            .padding(horizontal = 14.dp)
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Завершенные",
            style = DecideTheme.typography.titleLarge,
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

                PassedScreenState.Empty -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Здесь появится информация",
                            style = DecideTheme.typography.bodyLarge,
                            color = DecideTheme.colors.inputBlack
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Когда Вы пройдете тест",
                            style = DecideTheme.typography.bodyLarge,
                            color = DecideTheme.colors.inputBlack
                        )
                    }
                }


                is PassedScreenState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        ErrorMessage()
                    }
                }

                PassedScreenState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircleDecideIndicator()
                    }
                }
            }
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewPassedScreenLoading() {
    val state by remember {
        mutableStateOf(PassedScreenState.Initial)
    }
    DecideTheme {
        Column {
            PassedScreen(
                onClickResult = { id, date -> },
                state = PassedScreenState.Loading,
                onEvent = {})
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewPassedScreenEmpty() {
    val state by remember {
        mutableStateOf(PassedScreenState.Initial)
    }
    DecideTheme {
        Column {
            PassedScreen(
                onClickResult = { id, date -> },
                state = PassedScreenState.Empty,
                onEvent = {})
        }
    }
}
