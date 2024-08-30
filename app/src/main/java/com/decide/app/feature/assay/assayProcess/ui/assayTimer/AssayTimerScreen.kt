package com.decide.app.feature.assay.assayProcess.ui.assayTimer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.feature.assay.mainAssay.modals.AnswerAssay
import com.decide.app.feature.assay.mainAssay.modals.QuestionAssay
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.ErrorMessage
import com.decide.uikit.ui.buttons.ButtonBackArrow
import com.decide.uikit.ui.buttons.ButtonMain
import com.decide.uikit.ui.buttons.ButtonVariant
import com.decide.uikit.ui.buttons.CircleDecideIndicator
import com.decide.uikit.ui.card.CardQuestion
import java.util.Locale

@Composable
fun AssayTimerScreen(
    onClickDone: (argument: Int) -> Unit, onClickBack: () -> Unit
) {
    val viewModel: AssayTimerViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val timer by viewModel.countValue.collectAsStateWithLifecycle()

    AssayTimerScreen(timer = timer,
        state = state,
        onClickDone = { onClickDone(it) },
        onClickBack = { onClickBack() },
        onEvent = { event ->
            viewModel.onEvent(event)
        })
}

@Composable
fun AssayTimerScreen(
    timer: Long,
    state: AssayTimerState,
    onClickDone: (argument: Int) -> Unit,
    onClickBack: () -> Unit,
    onEvent: (event: EventAssayTimer) -> Unit,
) {

    when (state) {

        is AssayTimerState.Loaded -> {
            AssayWithTimer(timer = timer,
                questionAssay = state.question,
                progress = state.progress,
                onClickBack = { onClickBack() },
                onEvent = { onEvent(it) })

        }

        AssayTimerState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircleDecideIndicator()
            }
        }

        is AssayTimerState.End -> {
            onClickDone(state.idAssay)
        }

        AssayTimerState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ErrorMessage()
            }
        }

        AssayTimerState.TimeOver -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Время закончилось",
                    style = DecideTheme.typography.titleMedium,
                    color = DecideTheme.colors.inputBlack,
                )


                ButtonMain(
                    modifier = Modifier
                        .padding(bottom = 10.dp),
                    text = "Результат",
                    isActive = true
                ) {
                    onEvent(EventAssayTimer.StartTimer)
                }
            }
        }

        AssayTimerState.IsReady -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Готовы?",
                    style = DecideTheme.typography.titleMedium,
                    color = DecideTheme.colors.inputBlack,
                )

                ButtonMain(
                    modifier = Modifier
                        .padding(bottom = 10.dp),
                    text = "ГОТОВ",
                    isActive = true
                ) {
                    onEvent(EventAssayTimer.StartTimer)
                }
            }
        }
    }
}

@Composable
fun AssayWithTimer(
    timer: Long,
    questionAssay: QuestionAssay,
    progress: Float,
    onClickBack: () -> Unit,
    onEvent: (event: EventAssayTimer) -> Unit
) {

    var previousItem by remember {
        mutableIntStateOf(-1)
    }

    var selectedValue by rememberSaveable {
        mutableStateOf(emptyList<Int>())
    }

    val isSelectedItem: (Int) -> Boolean = {
        selectedValue.contains(it)
    }

    val onChangeState = { value: Int ->
        selectedValue = if (selectedValue.contains(value)) {
            selectedValue.filter { it != value }
        } else {
            if (selectedValue.size < questionAssay.countResponses) {
                selectedValue + value
            } else {
                selectedValue
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp),
    ) {
        ButtonBackArrow(text = "Назад", onClick = { onClickBack() })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LinearProgressIndicator(
                modifier = Modifier.clip(RoundedCornerShape(40.dp)),
                progress = { progress },
                color = DecideTheme.colors.accentYellow
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = formatTime(timer),
                style = DecideTheme.typography.titleMedium,
                color = DecideTheme.colors.inputBlack,
            )

            Spacer(modifier = Modifier.height(22.dp))

        }

        Box(modifier = Modifier.fillMaxHeight()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                CardQuestion(text = questionAssay.text)

                Spacer(modifier = Modifier.height(32.dp))

                Column {
                    questionAssay.listAnswers.forEach {
                        Spacer(modifier = Modifier.height(12.dp))
                        ButtonVariant(text = it.text, selected = isSelectedItem(it.id), onClick = {
                            onChangeState(it.id)
                        })
                    }
                    Spacer(modifier = Modifier.height(84.dp))
                }

            }

            ButtonMain(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
                text = "Выбрать",
                isActive = selectedValue.size == questionAssay.countResponses
            ) {
                if (selectedValue.size == questionAssay.countResponses) {
                    onEvent(
                        EventAssayTimer.selectedAnswer(
                            idQuestion = questionAssay.id,
                            idAnswer = selectedValue,
                            answerValue = selectedValue.map { item ->
                                questionAssay.listAnswers.find { it.id == item }?.value ?: -1f
                            }.toList()
                        )
                    )
                    selectedValue = emptyList()

                }
            }
        }
    }
}

private fun formatTime(timeSeconds: Long): String {
    val minutes = timeSeconds / 60
    val seconds = timeSeconds % 60

    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}

@Preview(showBackground = true)
@Composable
fun PreviewLoadedAssayWithTimer() {
    val state by remember {
        mutableStateOf(
            AssayTimerState.Loaded(
                question = QuestionAssay(
                    id = 1,
                    text = "aksldjanlsjdk alshbd asljkjdbahsbd alsjbd",
                    listAnswers = listOf(
                        AnswerAssay(
                            id = 1, text = "asd,asdbahsmd", value = 1f
                        ),
                        AnswerAssay(
                            id = 1, text = "asd,asdbahsmd", value = 1f
                        ),
                        AnswerAssay(
                            id = 1, text = "asd,asdbahsmd", value = 1f
                        ),
                    ),
                    image = "",
                    countResponses = 1
                ), progress = 3f
            )
        )
    }
    DecideTheme {
        Column {
            AssayTimerScreen(timer = 180,
                state = state,
                onClickDone = {},
                onClickBack = { /*TODO*/ }) {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimeOver() {
    val state by remember {
        mutableStateOf(
            AssayTimerState.TimeOver
        )
    }
    DecideTheme {
        Column {
            AssayTimerScreen(timer = 180,
                state = state,
                onClickDone = {},
                onClickBack = { /*TODO*/ }) {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIsReady() {
    val state by remember {
        mutableStateOf(
            AssayTimerState.IsReady
        )
    }
    DecideTheme {
        Column {
            AssayTimerScreen(timer = 180,
                state = state,
                onClickDone = {},
                onClickBack = { /*TODO*/ }) {

            }
        }
    }
}