package com.decide.app.feature.assay.assayResult.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.ErrorMessage
import com.decide.uikit.ui.buttons.ButtonMain
import com.decide.uikit.ui.buttons.CircleDecideIndicator

@Composable
fun AssayWithResultScreen(
    onClickExit: () -> Unit
) {

    val viewModel: AssayWithResultViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    AssayWithResultScreen(
        onClickExit = onClickExit, state = state
    )

    BackHandler {
        onClickExit()
    }
}


@Composable
fun AssayWithResultScreen(
    onClickExit: () -> Unit,
    state: AssayWithResultState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (state) {
            is AssayWithResultState.Loaded -> {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    if (state.result.results.size > 1) {
                        Text(
                            text = "Ваш результат:",
                            style = DecideTheme.typography.titleLarge,
                            color = DecideTheme.colors.inputBlack,
                        )
                    } else {
                        Text(
                            text = "Ваши результаты:",
                            style = DecideTheme.typography.titleLarge,
                            color = DecideTheme.colors.inputBlack,
                        )
                    }

                    state.result.results.forEachIndexed { index, s ->
                        Spacer(modifier = Modifier.height(4.dp))
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = DecideTheme.colors.gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            Text(
                                text = "Результат: ",
                                style = DecideTheme.typography.titleLarge,
                                color = DecideTheme.colors.inputBlack,
                            )
                            Text(
                                text = state.result.shortResults[index],
                                style = DecideTheme.typography.titleMedium,
                                color = DecideTheme.colors.inputBlack,
                                textAlign = TextAlign.End
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = s,
                            style = DecideTheme.typography.bodyLarge,
                            color = DecideTheme.colors.inputBlack,
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "**Однако важно помнить, что результаты теста не могут заменить профессиональную консультацию специалиста. Если у вас есть какие-либо вопросы или сомнения относительно своего эмоционального состояния, рекомендуется обратиться к психологу или психотерапевту. Они помогут вам разобраться в своих чувствах и при необходимости предложат способы улучшения вашего самочувствия.",
                            style = DecideTheme.typography.bodySmall,
                            color = DecideTheme.colors.mainGreen40,
                        )
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))
                ButtonMain(modifier = Modifier.padding(bottom = 10.dp), text = "Выход", onClick = {
                    onClickExit()
                })
            }

            AssayWithResultState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(top = 60.dp),
                        text = "Анализируем...",
                        style = DecideTheme.typography.titleSmall,
                        color = DecideTheme.colors.inputBlack,
                    )
                    Spacer(modifier = Modifier.height(84.dp))
                    CircleDecideIndicator()
                }
            }

            AssayWithResultState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ErrorMessage()
                }
            }
        }
    }
}

/**
 * AssayWithResultState.Loaded
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAssayWithResultScreenLoaded() {
    val state: AssayWithResultState by remember {
        mutableStateOf(
            AssayWithResultState.Loaded(
                ResultCompletedAssay(
                    date = 123123123,
                    shortResults = listOf("Short resulwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwt"),
                    results = listOf("Result"),
                    keyResults = listOf(1)
                )
            )
        )
    }
    DecideTheme {
        Column {
            AssayWithResultScreen(state = state, onClickExit = {})
        }
    }
}

/**
 * AssayWithResultState.Loading
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAssayWithResultScreenLoading() {
    DecideTheme {
        Column {
            AssayWithResultScreen(state = AssayWithResultState.Loading, onClickExit = {})
        }
    }
}

/**
 * AssayWithResultState.Loading
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAssayWithResultScreenError() {
    DecideTheme {
        Column {
            AssayWithResultScreen(state = AssayWithResultState.Error, onClickExit = {})
        }
    }
}