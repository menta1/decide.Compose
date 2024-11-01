package com.decide.app.feature.passed.ui.showPassedResult

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.app.utils.dateFormatter
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.ErrorMessage
import com.decide.uikit.ui.buttons.ButtonMain
import com.decide.uikit.ui.buttons.CircleDecideIndicator

@Composable
fun ShowPassedResultScreen(
    onClickExit: () -> Unit
) {
    val viewModel: ShowPassedResultViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ShowPassedResultScreen(
        state = state,
        onClickExit = onClickExit
    )
}

@Composable
private fun ShowPassedResultScreen(
    state: ShowPassedResultState,
    onClickExit: () -> Unit
) {
    when (state) {
        ShowPassedResultState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ErrorMessage()
            }
        }

        ShowPassedResultState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircleDecideIndicator()
            }
        }

        is ShowPassedResultState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DecideTheme.colors.background)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 14.dp)
                            .padding(top = 8.dp),
                        text = state.nameAssay,
                        style = DecideTheme.typography.titleSmall,
                        color = DecideTheme.colors.text
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 14.dp)
                            .padding(top = 8.dp),
                        text = dateFormatter(state.result.date),
                        style = DecideTheme.typography.bodyLarge,
                        color = DecideTheme.colors.text
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        state.result.shortResults.forEachIndexed { index, value ->
                            Spacer(modifier = Modifier.height(16.dp))
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp),
                                colors = CardDefaults.elevatedCardColors().copy(
                                    containerColor = DecideTheme.colors.container,
                                    contentColor = DecideTheme.colors.text
                                ),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 4.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = value,
                                        style = DecideTheme.typography.titleMedium,
                                        color = DecideTheme.colors.text
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = state.result.results[index],
                                        style = DecideTheme.typography.bodyMedium,
                                        color = DecideTheme.colors.text
                                    )
                                }

                            }
                        }
                    }

                    ButtonMain(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .padding(horizontal = 14.dp),
                        text = "Выход",
                    ) {
                        onClickExit()
                    }
                }

            }
        }
    }
}

@MainPreview
@Composable
private fun Preview() {
    val state: ShowPassedResultState by remember {
        mutableStateOf(
            ShowPassedResultState.Success(
                ResultCompletedAssay(
                    date = 123123123,
                    shortResults = listOf("Short result", "Short result", "Short result"),
                    results = listOf("Result", "Result", "Result"),
                    keyResults = listOf(1)
                ),
                nameAssay = "Анализ крови Оценка социальной неудовлетворенности"
            )
        )
    }
    DecideTheme {
        Column {
            ShowPassedResultScreen(state = state, onClickExit = {})
        }
    }
}
