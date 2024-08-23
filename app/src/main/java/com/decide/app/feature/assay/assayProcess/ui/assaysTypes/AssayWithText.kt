package com.decide.app.feature.assay.assayProcess.ui.assaysTypes

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.decide.app.feature.assay.assayProcess.ui.AssayProcessState
import com.decide.app.feature.assay.assayProcess.ui.EventAssayProcess
import com.decide.app.feature.assay.mainAssay.modals.QuestionAssay
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ButtonBackArrow
import com.decide.uikit.ui.buttons.ButtonMain
import com.decide.uikit.ui.buttons.ButtonVariant
import com.decide.uikit.ui.card.CardQuestion

@Composable
fun AssayWithText(
    state: AssayProcessState,
    questionAssay: QuestionAssay,
    progress: Float,
    onClickBack: () -> Unit,
    onEvent: (event: EventAssayProcess) -> Unit
) {

    var answer by remember {
        mutableIntStateOf(-1)
    }

    var selectedValue by remember { mutableIntStateOf(0) }

    val isSelectedItem: (Int) -> Boolean = { selectedValue == it }
    val onChangeState: (Int) -> Unit = { selectedValue = it }

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
                        ButtonVariant(
                            text = it.text,
                            selected = isSelectedItem(it.id),
                            onClick = {
                                onChangeState(it.id)
                                answer = it.id
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(84.dp))
                }

            }

            ButtonMain(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
                text = "Выбрать",
                isActive = answer != -1
            ) {
                if (answer != -1) {
                    onEvent(
                        EventAssayProcess(
                            idQuestion = questionAssay.id,
                            idAnswer = answer,
                            answerValue = questionAssay.listAnswers.find { it.id == answer }?.value
                                ?: -1f
                        )
                    )
                    answer = -1
                    selectedValue = 0

                }
            }
        }
    }
}