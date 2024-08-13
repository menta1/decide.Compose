package com.decide.app.feature.passed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.R
import com.decide.app.feature.passed.PassedAssay
import com.decide.app.utils.dateFormatter
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.card.CardResultAssay

@Composable
fun PassedScreen(
    modifier: Modifier = Modifier,
    onClickResult: (id: Int) -> Unit,
) {

    val viewModel: PassedScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    PassedScreen(
        modifier = modifier,
        onClickResult = onClickResult,
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun PassedScreen(
    modifier: Modifier = Modifier,
    onClickResult: (id: Int) -> Unit,
    state: PassedScreenState,
    onEvent: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DecideTheme.colors.mainBlue)
            .padding(top = 36.dp)
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Завершенные",
            style = DecideTheme.typography.titleScreen,
            color = DecideTheme.colors.inputBlack
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is PassedScreenState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(items = state.assays) { passedAssay: PassedAssay ->
                            CardResultAssay(
                                image = painterResource(id = R.drawable.category11),
                                textCategory = passedAssay.nameCategory,
                                textAssay = passedAssay.name,
                                textDate = dateFormatter(passedAssay.results.last().date),
                                results = passedAssay.results.map { it.date }
                            ) {

                            }
                        }
                    }
                }

                PassedScreenState.Default -> {

                }


                is PassedScreenState.Error -> {

                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewPassedScreen() {
    val state by remember {
        mutableStateOf(PassedScreenState.Initial)
    }
    DecideTheme {
        Column {
            PassedScreen(onClickResult = {}, state = state) {

            }
        }
    }
}