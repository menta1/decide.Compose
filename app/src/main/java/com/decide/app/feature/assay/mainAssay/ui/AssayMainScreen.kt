package com.decide.app.feature.assay.mainAssay.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.card.CardAssay
import com.decide.uikit.ui.searchBar.SearchBarDecide
import kotlinx.collections.immutable.toImmutableList

@Composable
fun AssayMainScreen(
    modifier: Modifier = Modifier,
    onClickAssay: (argument: Int) -> Unit,
) {
    val viewModel: AssayMainViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    AssayMainScreen(
        modifier = modifier,
        state = state,
        onChangeSearch = {},
        onClickAssay = onClickAssay
    )
}

@Composable
fun AssayMainScreen(
    modifier: Modifier = Modifier,
    state: StateAssay,
    onChangeSearch: (text: String) -> Unit,
    onClickAssay: (argument: Int) -> Unit,
) {
    val textSearch by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier//надо передавать сюда сверху
            .fillMaxSize()
            .background(DecideTheme.colors.mainBlue)
            .padding(horizontal = 15.dp)
            .padding(top = 70.dp),
    ) {
        Text(
            text = "Психологические тесты",
            style = DecideTheme.typography.titleScreen,
            color = DecideTheme.colors.inputBlack
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchBarDecide(
            value = textSearch,
            onValueChange = onChangeSearch,
        )
        when (state) {
            is StateAssay.Success -> {
                LazyColumn {
                    itemsIndexed(state.assays) { index, assay ->
                        Spacer(modifier = Modifier.height(16.dp))
                        CardAssay(
                            image = painterResource(id = com.decide.uikit.R.drawable.category_capabilities),
                            icon = painterResource(id = com.decide.uikit.R.drawable.ic_star_rating),
                            textCategory = assay.nameCategory,
                            textAssay = assay.name,
                            textRating = assay.rating,
                            textQuestion = assay.countQuestions,
                            onClickBookmark = {

                            },
                            onClickAssay = {
                                Log.d("TAG", "itemsIndexed 88 = $index data=${assay.name}")
                                onClickAssay(index + 1)
                            }
                        )
                    }
                }
            }

            is StateAssay.Error -> {}
            StateAssay.None -> {}
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAssayMainScreen() {
    val state: StateAssay by remember {
        mutableStateOf(
            StateAssay.Success(
                listOf(
                    AssayUI(
                        id = 1,
                        name = "Оценка социальной неудовлетворенности",
                        nameCategory = "Психическое состояние",
                        countQuestions = "34",
                        rating = "3.3"
                    ),
                    AssayUI(
                        id = 1,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                ).toImmutableList()
            )
        )
    }
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            AssayMainScreen(state = state, onChangeSearch = {}, onClickAssay = {})
        }
    }
}
