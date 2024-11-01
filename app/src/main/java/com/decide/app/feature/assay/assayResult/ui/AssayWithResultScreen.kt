package com.decide.app.feature.assay.assayResult.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.uikit.R
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.ErrorMessage
import com.decide.uikit.ui.buttons.ButtonMain
import com.decide.uikit.ui.buttons.CircleDecideIndicator
import kotlinx.coroutines.delay

@Composable
fun AssayWithResultScreen(
    onClickExit: () -> Unit,

    ) {

    val viewModel: AssayWithResultViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()


    AssayWithResultScreen(
        onClickExit = onClickExit,
        state = state,
        onEvent = viewModel::onEvent
    )

}


@Composable
private fun AssayWithResultScreen(
    onClickExit: () -> Unit,
    state: AssayWithResultState,
    onEvent: (Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var showedDialog by remember { mutableStateOf(false) }


    BackHandler {
        if (!showDialog) showDialog = true

        if (showedDialog) onClickExit()
    }

    when (state) {
        is AssayWithResultState.Loaded -> {
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
                        text = "Результат",
                        style = DecideTheme.typography.headlineLarge,
                        color = DecideTheme.colors.text
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
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
                                        style = DecideTheme.typography.titleLarge,
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
                            .padding(top = 12.dp)
                            .padding(bottom = 10.dp)
                            .padding(horizontal = 14.dp),
                        text = "Выход",
                    ) {

//                        if (!showDialog) showDialog = true
//
//                        if (showedDialog)
                        onClickExit()
                    }

//                    if (showDialog && !showedDialog) {
//                        DialogRating(
//                            onDismissRequest = {
//                                showedDialog = true
//                            },
//                            onClick = {
//                                showedDialog = true
//                                onEvent(it)
//                            }
//                        )
//                    }

                }

            }
        }

        AssayWithResultState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
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

@Composable
private fun DialogRating(
    onDismissRequest: () -> Unit,
    onClick: (star: Int) -> Unit
) {
    var countSelected by remember { mutableIntStateOf(0) }
    val animColor by animateColorAsState(
        targetValue = DecideTheme.colors.accentYellow, label = ""
    )

    var starSelected by remember { mutableStateOf(false) }

    if (starSelected) {
        LaunchedEffect(key1 = Unit) {
            delay(500)
            onClick(countSelected)
        }
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            colors = CardDefaults.cardColors().copy(
                containerColor = DecideTheme.colors.background
            )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .padding(horizontal = 2.dp),
                text = "Оцените тест",
                style = DecideTheme.typography.titleLarge,
                color = DecideTheme.colors.text,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                for (i in 1..5) {
                    IconButton(onClick = {
                        countSelected = i
                        starSelected = true
                        onClick(i)
                    }) {
                        Icon(
                            modifier = Modifier
                                .animateContentSize()
                                .size(if (i == countSelected) 34.dp else 24.dp),
                            painter = painterResource(R.drawable.ic_star),
                            tint = if (i > countSelected) {
                                DecideTheme.colors.gray
                            } else {
                                animColor
                            },
                            contentDescription = ""
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(34.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier.clickable(
                        indication = ripple(
                            radius = 30.dp,
                            color = DecideTheme.colors.text
                        ), interactionSource = remember { MutableInteractionSource() }, onClick = {
                            onDismissRequest()
                        })
                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .padding(bottom = 8.dp),
                        text = "Не хочу",
                        style = DecideTheme.typography.titleMedium,
                        color = DecideTheme.colors.text
                    )
                }

            }
        }
    }
}

@MainPreview
@Composable
private fun Preview() {
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
            AssayWithResultScreen(state = state, onClickExit = {}, onEvent = {})
        }
    }
}

@MainPreview
@Composable
fun PreviewDialogRating() {
    DecideTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DialogRating({}, {})
        }
    }
}
