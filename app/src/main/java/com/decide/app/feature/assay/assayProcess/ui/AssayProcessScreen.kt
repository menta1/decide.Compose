package com.decide.app.feature.assay.assayProcess.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.feature.assay.assayProcess.ui.assaysTypes.AssayWithText
import com.decide.uikit.ui.buttons.CircleDecideIndicator

@Composable
fun AssayProcessScreen(
    onClickDone: (argument: Int) -> Unit,
    onClickBack: () -> Unit
) {
    val viewModel: AssayProcessViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    AssayProcessScreen(
        state = state,
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
                questionAssay = state.question,
                progress = state.progress,
                onClickBack = { onClickBack() },
                onEvent = { onEvent(it) })

        }

        is AssayProcessState.AssayWithTimer -> {
        }

        AssayProcessState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircleDecideIndicator()
            }
        }

        is AssayProcessState.End -> {
            onClickDone(state.idAssay)
        }

        AssayProcessState.Error -> {
        }
    }
}