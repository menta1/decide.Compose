package com.decide.app.feature.assay.assayProcess.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.feature.assay.mainAssay.modals.QuestionAssay
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ButtonBackArrow
import com.decide.uikit.ui.buttons.ButtonMain
import com.decide.uikit.ui.buttons.ButtonVariant
import com.decide.uikit.ui.card.CardQuestion

@Composable
fun AssayProcessScreen(
    modifier: Modifier = Modifier,
    idAssay: Int?,
    viewModel: AssayProcessViewModel = hiltViewModel(),
    onClickDone: (argument: Int) -> Unit,
    onClickBack: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    AssayContent(
        state = state,
        modifier = modifier,
        onClickStart = { onClickDone(it) },
        onClickBack = {
            Log.d("TAG", "onClickBack")
            onClickBack() }) { event ->
        viewModel.onEvent(event)
    }
}

@Composable
fun AssayContent(
    state: AssayProcessState,
    modifier: Modifier,
    onClickStart: (argument: Int) -> Unit,
    onClickBack: () -> Unit,
    onEvent: (event: EventAssayProcess) -> Unit,
) {

    when (state) {
        is AssayProcessState.AssayWithImage -> {
            Log.d("TAG", "AssayWithImage")
        }

        is AssayProcessState.AssayWithText -> {
            AssayWithText(
                questionAssay = state.question,
                progress = state.progress,
                onClickStart = { onClickStart(it) },
                onClickBack = { onClickBack() })
            { onEvent(it) }
        }

        is AssayProcessState.AssayWithTimer -> {
            Log.d("TAG", "AssayWithTimer")
        }

        AssayProcessState.Default -> {
            Log.d("TAG", "Default")
        }

        AssayProcessState.End -> {
            Log.d("TAG", "End")
        }

        AssayProcessState.Error -> {
            Log.d("TAG", "Error")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun     AssayWithText(
    questionAssay: QuestionAssay,
    progress: Float,
    onClickStart: (argument: Int) -> Unit,
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
            .padding(horizontal = 15.dp)
            .background(DecideTheme.colors.mainBlue),
    ) {
        ButtonBackArrow(text = "Назад", onClick = { onClickBack() })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
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
                    //.fillMaxWidth()
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
                            selected = isSelectedItem(it.id)
                        ) {
                            onChangeState(it.id)
                            answer = it.id
                        }
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
                    answer = -1
                    selectedValue = 0
                    onEvent(EventAssayProcess(questionAssay.id, answer))
                }

            }
        }
    }
}


@Composable
fun AssayWithImage() {

    val progress by remember {
        mutableFloatStateOf(0.4f)
    }

    var asd: List<String> = listOf("Paypal", "Google Pay", "Apple Pay", "Apple Pay")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Назад",
                style = DecideTheme.typography.titleScreen,
                color = DecideTheme.colors.inputBlack,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LinearProgressIndicator(
                modifier = Modifier.clip(RoundedCornerShape(40.dp)),
                progress = { progress },
                color = DecideTheme.colors.accentYellow
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "03:23",
                style = DecideTheme.typography.titleScreen,
                color = DecideTheme.colors.inputBlack,
            )

            Spacer(modifier = Modifier.height(12.dp))

        }

        Box(modifier = Modifier.fillMaxHeight()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CardQuestion(text = "Nicholas Savage: You know what’s weird? That quote has been attributed to Bataille, to McLuhan, to Andy Warhol: it's one of those.")

                Spacer(modifier = Modifier.height(32.dp))

                Image(
                    modifier = Modifier
                        .heightIn(max = 256.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    painter = painterResource(id = R.drawable.category_capabilities),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(32.dp))

                Column {
                    asd.forEach {
                        Spacer(modifier = Modifier.height(12.dp))
                        ButtonVariant(
                            text = it
                        ) {

                        }
                    }
                }
                Spacer(modifier = Modifier.height(84.dp))
            }
            ButtonMain(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
                text = "Выбрать"
            ) {

            }
        }
    }
}

@Composable
fun AssayWithTimer() {

    val progress by remember {
        mutableFloatStateOf(0.4f)
    }

    var asd: List<String> = listOf("Paypal", "Google Pay", "Apple Pay", "Apple Pay")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Назад",
                style = DecideTheme.typography.titleScreen,
                color = DecideTheme.colors.inputBlack,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LinearProgressIndicator(
                modifier = Modifier.clip(RoundedCornerShape(40.dp)),
                progress = { progress },
                color = DecideTheme.colors.accentYellow
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "03:23",
                style = DecideTheme.typography.titleScreen,
                color = DecideTheme.colors.inputBlack,
            )

            Spacer(modifier = Modifier.height(12.dp))

        }

        Box(modifier = Modifier.fillMaxHeight()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CardQuestion(text = "Nicholas Savage: You know what’s weird? That quote has been attributed to Bataille, to McLuhan, to Andy Warhol: it's one of those.")

                Spacer(modifier = Modifier.height(32.dp))

                Image(
                    modifier = Modifier
                        .heightIn(max = 256.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    painter = painterResource(id = R.drawable.category_capabilities),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(32.dp))

                Column {
                    asd.forEach {
                        Spacer(modifier = Modifier.height(12.dp))
                        ButtonVariant(
                            text = it
                        ) {

                        }
                    }
                }
                Spacer(modifier = Modifier.height(84.dp))
            }
            ButtonMain(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
                text = "Выбрать"
            ) {

            }
        }
    }
}

@Composable
fun ErrorMessage() {

}