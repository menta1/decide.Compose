package com.decide.app.feature.assay.assayResult.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ButtonMain

@Composable
fun AssayWithResultScreen(
    modifier: Modifier = Modifier,
    onClickExit: () -> Unit
) {
    val viewModel: AssayWithResultViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    AssayWithResultScreen(
        modifier = modifier,
        onClickExit = onClickExit,
        state = state
    )
}


@Composable
fun AssayWithResultScreen(
    modifier: Modifier = Modifier,
    onClickExit: () -> Unit,
    state: AssayWithResultState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DecideTheme.colors.mainBlue)
            .padding(top = 64.dp)
            .padding(vertical = 28.dp)
    ) {
        when (state) {
            AssayWithResultState.Default -> TODO()
            AssayWithResultState.Error -> TODO()
            is AssayWithResultState.Loaded -> {
                Text(text = state.result)
            }

            AssayWithResultState.Loading -> {

            }
        }

        ButtonMain(
            modifier = Modifier
                .padding(bottom = 10.dp),
            text = "Выбрать",
        ) {
            onClickExit()
        }

    }
}

@Preview
@Composable
fun PreviewAssayWithResultScreen(){
    val state: AssayWithResultState by remember {
mutableStateOf(AssayWithResultState.Initial)
    }
    DecideTheme{
        Column {
            AssayWithResultScreen(state = state, onClickExit = {}, )
        }
    }
}