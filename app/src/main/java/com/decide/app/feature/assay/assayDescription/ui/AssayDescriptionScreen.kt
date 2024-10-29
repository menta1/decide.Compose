package com.decide.app.feature.assay.assayDescription.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.ErrorMessage
import com.decide.uikit.ui.buttons.ButtonBackArrow
import com.decide.uikit.ui.buttons.ButtonMain
import com.decide.uikit.ui.buttons.CircleDecideIndicator

@Composable
fun AssayDescriptionScreen(
    idAssay: Int?,
    viewModel: AssayDescriptionViewModel = hiltViewModel(),
    onStartAssayText: (argument: Int) -> Unit,
    onStartAssayTimer: (argument: Int) -> Unit,
    onClickBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AssayDescriptionScreen(
        state = state,
        onStartAssayText = { onStartAssayText(idAssay ?: -1) },
        onStartAssayTimer = { onStartAssayTimer(idAssay ?: -1) },
        onClickBack = onClickBack
    )
}

@Composable
private fun AssayDescriptionScreen(
    state: AssayDescriptionState,
    onStartAssayText: () -> Unit,
    onStartAssayTimer: () -> Unit,
    onClickBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp),
    ) {
        ButtonBackArrow(text = "Назад", onClick = { onClickBack() })

        when (state) {
            is AssayDescriptionState.Loaded -> {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = 60.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(bottom = 4.dp),
                            text = state.description,
                            style = DecideTheme.typography.bodyLarge,
                            color = DecideTheme.colors.text,
                        )

                    }
                    ButtonMain(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 10.dp),
                        text = "Начать"
                    ) {
                        when (state.typeAssay) {
                            1 -> {
                                onStartAssayText()
                            }

                            2 -> {
                                onStartAssayTimer()
                            }
                        }

                    }
                }
            }

            AssayDescriptionState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircleDecideIndicator()
                }
            }

            AssayDescriptionState.Error -> {
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

@MainPreview
@Composable
private fun Preview() {
    DecideTheme {
        val state by remember {
            mutableStateOf(
                AssayDescriptionState.Loaded(
                    "Цель: выявить состояwwwwwwwwwwwwwЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнЦель: выявить состояwwwwwwwwwwwwwwwwwwwwние тревожнwwwwwwwние тревожности и депрессии, обусловленное неуравновешенностью нервных процессов.",
                    1
                )
            )
        }
        AssayDescriptionScreen(
            state = state,
            onClickBack = {},
            onStartAssayText = {},
            onStartAssayTimer = {})
    }
}