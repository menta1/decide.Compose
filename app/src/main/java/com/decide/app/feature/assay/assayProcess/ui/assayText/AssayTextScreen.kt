package com.decide.app.feature.assay.assayProcess.ui.assayText

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.feature.assay.assayMain.modals.QuestionAssay
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ButtonBackArrow
import com.decide.uikit.ui.buttons.ButtonMain
import com.decide.uikit.ui.buttons.ButtonVariant
import com.decide.uikit.ui.buttons.CircleDecideIndicator
import com.decide.uikit.ui.card.CardQuestion

@Composable
fun AssayTextScreen(
    onClickDone: (argument: Int) -> Unit,
    onClickBack: () -> Unit
) {
    val viewModel: AssayTextViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    AssayTextScreen(state = state,
        onClickDone = { onClickDone(it) },
        onClickBack = { onClickBack() },
        onEvent = { event ->
            viewModel.onEvent(event)
        })
}

@Composable
fun AssayTextScreen(
    state: AssayTextState,
    onClickDone: (argument: Int) -> Unit,
    onClickBack: () -> Unit,
    onEvent: (event: EventAssayText) -> Unit,
) {

    when (state) {

        is AssayTextState.Loaded -> {
            AssayWithText(state = state,
                questionAssay = state.question,
                progress = state.progress,
                onClickBack = { onClickBack() },
                onEvent = { onEvent(it) })

        }

        AssayTextState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircleDecideIndicator()
            }
        }

        is AssayTextState.End -> {
            onClickDone(state.idAssay)
        }

        AssayTextState.Error -> {
        }
    }
}

@Composable
fun AssayWithText(
    state: AssayTextState,
    questionAssay: QuestionAssay,
    progress: Float,
    onClickBack: () -> Unit,
    onEvent: (event: EventAssayText) -> Unit
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
                        EventAssayText(
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