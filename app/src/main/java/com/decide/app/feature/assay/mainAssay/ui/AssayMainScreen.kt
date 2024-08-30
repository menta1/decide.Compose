package com.decide.app.feature.assay.mainAssay.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.decide.app.R
import com.decide.app.utils.setDrawable
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.ErrorMessage
import com.decide.uikit.ui.buttons.CircleDecideIndicator
import com.decide.uikit.ui.card.CardAssay
import com.decide.uikit.ui.searchBar.SearchBarDecide
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun AssayMainScreen(
    onClickAssay: (argument: Int) -> Unit,
) {
    val viewModel: AssayMainViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    AssayMainScreen(
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
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
            .padding(top = 8.dp),
    ) {
        Text(
            text = "Психологические тесты",
            style = DecideTheme.typography.titleLarge,
            color = DecideTheme.colors.inputBlack
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchBarDecide(
            value = textSearch,
            onValueChange = onChangeSearch,
        )
        AnimatedContent(
            state,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(500)
                ) togetherWith fadeOut(animationSpec = tween(300))
            },
            label = "Animated Content"
        ) { targetState ->
            when (targetState) {

                is StateAssay.Error -> Error()
                is StateAssay.Loaded -> Loaded(
                    assays = targetState.assays,
                    onClickAssay = onClickAssay
                )

                StateAssay.Loading -> Loading()
            }
        }

    }
}

@Composable
internal fun Loaded(
    assays: ImmutableList<AssayUI>,
    onClickAssay: (argument: Int) -> Unit,
) {
    LazyColumn {
        items(assays, key = { item -> item.id }) { assay ->
            Spacer(modifier = Modifier.height(16.dp))
            CardAssay(
                image = painterResource(id = setDrawable(assay.idCategory)),
                icon = painterResource(id = com.decide.uikit.R.drawable.ic_star_rating),
                textCategory = assay.nameCategory,
                textAssay = assay.name,
                textRating = assay.rating,
                textQuestion = pluralStringResource(
                    id = R.plurals.questions,
                    count = assay.countQuestions.toInt(),
                    assay.countQuestions.toInt()
                ),
                onClickBookmark = {

                },
                onClickAssay = {
                    onClickAssay(assay.id)
                }
            )
        }
    }
}

@Composable
internal fun Error() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ErrorMessage()
    }
}

@Composable
internal fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircleDecideIndicator()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAssayMainScreen() {
    val state: StateAssay by remember {
        mutableStateOf(
            StateAssay.Loaded(
                listOf(
                    AssayUI(
                        id = 1,
                        name = "Оценка социальной неудовлетворенности",
                        idCategory = 1,
                        nameCategory = "Психическое состояние",
                        countQuestions = "34",
                        rating = "3.3"
                    ),
                    AssayUI(
                        id = 1,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 2,
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                    AssayUI(
                        id = 1,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 3,
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                    AssayUI(
                        id = 1,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 4,
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                    AssayUI(
                        id = 1,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 15,
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                    AssayUI(
                        id = 1,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 6,
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                    AssayUI(
                        id = 1,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 7,
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                    AssayUI(
                        id = 1,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 8,
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                    AssayUI(
                        id = 1,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 11231,
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAssayMainScreenLoading() {
    val state: StateAssay by remember {
        mutableStateOf(StateAssay.Loading)
    }
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            AssayMainScreen(state = state, onChangeSearch = {}, onClickAssay = {})
        }
    }
}