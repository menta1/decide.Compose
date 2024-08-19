package com.decide.app.feature.assay.assayProcess.ui

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.feature.assay.assayProcess.ui.AssaysTypes.AssayWithText
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
    onClickDone: (argument: Int) -> Unit,
    onClickBack: () -> Unit
) {
    val viewModel: AssayProcessViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    AssayProcessScreen(
        state = state,
        modifier = modifier,
        onClickDone = { onClickDone(it) },
        onClickBack = {
            onClickBack()
        }) { event ->
        viewModel.onEvent(event)
    }
}

@Composable
fun AssayProcessScreen(
    state: AssayProcessState,
    modifier: Modifier,
    onClickDone: (argument: Int) -> Unit,
    onClickBack: () -> Unit,
    onEvent: (event: EventAssayProcess) -> Unit,
) {

    when (state) {

        is AssayProcessState.AssayWithImage -> {
        }

        is AssayProcessState.AssayWithText -> {
            AssayWithText(
                state = state,
                modifier = modifier,
                questionAssay = state.question,
                progress = state.progress,
                onClickBack = { onClickBack() },
                onEvent = { onEvent(it) })

        }

        is AssayProcessState.AssayWithTimer -> {
        }

        AssayProcessState.Default -> {
        }

        is AssayProcessState.End -> {
            onClickDone(state.idAssay)
        }

        AssayProcessState.Error -> {
        }
    }
}


@Composable
fun ErrorMessage() {

}