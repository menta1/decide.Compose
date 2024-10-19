package com.decide.app.feature.assay.assayMain.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.R
import com.decide.app.feature.defaultScreens.ErrorScreen
import com.decide.app.feature.defaultScreens.LoadingScreen
import com.decide.app.feature.defaultScreens.NetworkErrorScreen
import com.decide.app.utils.setDrawable
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.card.CardAssay
import com.decide.uikit.ui.searchBar.SearchBarDecide
import kotlinx.collections.immutable.toImmutableList

@Composable
fun AssayMainScreen(
    onClickAssay: (argument: Int) -> Unit,
) {
    val viewModel: AssayMainViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    AssayMainScreen(
        state = state,
        onClickAssay = onClickAssay,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun AssayMainScreen(
    modifier: Modifier = Modifier,
    state: AssayMainState,
    onClickAssay: (argument: Int) -> Unit,
    onEvent: (event: AssayMainEvent) -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedVisibility(
            modifier = Modifier.zIndex(1f),
            visible = !state.scrollUp,
            enter = slideInVertically(animationSpec = tween(300)),
            exit = slideOutVertically(animationSpec = tween(300))
        ) {
            Column(
                modifier = Modifier
                    .shadow(
                        elevation = 2.dp
                    )

                    .background(DecideTheme.colors.background)

            ) {
                Text(
                    modifier = modifier
                        .padding(horizontal = 14.dp)
                        .padding(top = 8.dp),
                    text = "Психологические тесты",
                    style = DecideTheme.typography.titleLarge,
                    color = DecideTheme.colors.inputBlack
                )
                Spacer(modifier = Modifier.height(16.dp))
                SearchBarDecide(
                    modifier = modifier
                        .padding(horizontal = 14.dp),
                    value = state.searchText,
                    onValueChange = { onEvent(AssayMainEvent.SetSearch(it)) },
                )
            }

        }

        when (state.uiState) {
            UIState.LOADING -> LoadingScreen()
            UIState.ERROR -> ErrorScreen(
                text = "Попробовать еще раз"
            ) {
                onEvent(AssayMainEvent.Update)
            }

            UIState.NO_INTERNET -> {
                NetworkErrorScreen {
                    onEvent(AssayMainEvent.Update)
                }
            }

            UIState.SUCCESS -> Loaded(
                assays = state.assays,
                onClickAssay = onClickAssay,
                onEvent = onEvent
            )
        }
    }
}


@Composable
internal fun Loaded(
    assays: List<AssayUI>,
    onClickAssay: (argument: Int) -> Unit,
    onEvent: (event: AssayMainEvent) -> Unit
) {
    val scrollState = rememberLazyListState()

    val firstVisible by remember {
        derivedStateOf { scrollState.firstVisibleItemIndex }
    }
    onEvent(AssayMainEvent.ScrollState(firstVisible))

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 14.dp),
        state = scrollState
    ) {
        itemsIndexed(
            items = assays,
            key = { _, item -> item.id }
        ) { index, assay ->
            if (index == 0) {
                Spacer(modifier = Modifier.height(112.dp))
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }

            CardAssay(
                image = setDrawable(assay.idCategory),
                icon = painterResource(id = com.decide.uikit.R.drawable.ic_star_rating),
                textCategory = assay.nameCategory,
                textAssay = assay.name,
                textRating = assay.rating.ifEmpty { "0.0" },
                textQuestion = pluralStringResource(
                    id = R.plurals.questions,
                    count = assay.countQuestions.toInt(),
                    assay.countQuestions.toInt()
                ),
                onClickAssay = {
                    onClickAssay(assay.id)
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAssayMainScreen() {
    val state: AssayMainState by remember {
        mutableStateOf(
            AssayMainState(
                assays = sequenceOf(
                    AssayUI(
                        id = 1,
                        name = "Оценка социальной неудовлетворенности",
                        idCategory = 1,
                        nameCategory = "Психическое состояние",
                        countQuestions = "34",
                        rating = "3.3"
                    ),
                    AssayUI(
                        id = 2,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 2,
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                    AssayUI(
                        id = 3,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 3,
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                    AssayUI(
                        id = 4,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 4,
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                    AssayUI(
                        id = 5,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 15,
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                    AssayUI(
                        id = 6,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 6,
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                    AssayUI(
                        id = 7,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 7,
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                    AssayUI(
                        id = 8,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 8,
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                    AssayUI(
                        id = 9,
                        name = "Методика диагностики самооценки психических состояний\"\n",
                        idCategory = 11231,
                        nameCategory = "Психическое состояние",
                        countQuestions = "121",
                        rating = "03"
                    ),
                ).toImmutableList(),
                uiState = UIState.SUCCESS
            )
        )
    }
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            AssayMainScreen(state = state, onEvent = {}, onClickAssay = {})
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAssayMainScreenLoading() {
    val state: AssayMainState by remember {
        mutableStateOf(AssayMainState(uiState = UIState.LOADING))
    }
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            AssayMainScreen(state = state, onEvent = {}, onClickAssay = {})
        }
    }
}

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AssayMainScreenScaffold(
//    modifier: Modifier = Modifier,
//    state: AssayMainState,
//    onClickAssay: (argument: Int) -> Unit,
//    onEvent: (event: AssayMainEvent) -> Unit
//) {
//
//    val scrollBehavior =
//        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
//
//    Scaffold(
//        topBar = {
//            MediumTopAppBar(
//                title = {
//                    Column(
//                        modifier = modifier
//                            .padding(horizontal = 14.dp)
//                            .padding(top = 8.dp),
//                    ) {
//                        Text(
//                            text = "Психологические тесты",
//                            style = DecideTheme.typography.titleLarge,
//                            color = DecideTheme.colors.inputBlack
//                        )
//                        Spacer(modifier = Modifier.height(16.dp))
//                        SearchBarDecide(
//                            value = state.searchText,
//                            onValueChange = { onEvent(AssayMainEvent.SetSearch(it)) },
//                        )
//                    }
//                },
//                scrollBehavior = scrollBehavior
//            )
//        },
//        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier.padding(innerPadding)
//        ) {
//            when (state.uiState) {
//                UIState.LOADING -> LoadingScreen()
//                UIState.ERROR -> ErrorScreen(
//                    text = "Попробовать еще раз"
//                ) {
//                    onEvent(AssayMainEvent.Update)
//                }
//
//                UIState.NO_INTERNET -> {
//                    NetworkErrorScreen {
//                        onEvent(AssayMainEvent.Update)
//                    }
//                }
//
//                UIState.SUCCESS -> Loaded(
//                    assays = state.assays,
//                    onClickAssay = onClickAssay
//                )
//            }
//        }
//
//
//    }
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewAssayMainScreenScaffold() {
//    val state: AssayMainState by remember {
//        mutableStateOf(
//            AssayMainState(
//                assays = sequenceOf(
//                    AssayUI(
//                        id = 1,
//                        name = "Оценка социальной неудовлетворенности",
//                        idCategory = 1,
//                        nameCategory = "Психическое состояние",
//                        countQuestions = "34",
//                        rating = "3.3"
//                    ),
//                    AssayUI(
//                        id = 2,
//                        name = "Методика диагностики самооценки психических состояний\"\n",
//                        idCategory = 2,
//                        nameCategory = "Психическое состояние",
//                        countQuestions = "121",
//                        rating = "03"
//                    ),
//                    AssayUI(
//                        id = 3,
//                        name = "Методика диагностики самооценки психических состояний\"\n",
//                        idCategory = 3,
//                        nameCategory = "Психическое состояние",
//                        countQuestions = "121",
//                        rating = "03"
//                    ),
//                    AssayUI(
//                        id = 4,
//                        name = "Методика диагностики самооценки психических состояний\"\n",
//                        idCategory = 4,
//                        nameCategory = "Психическое состояние",
//                        countQuestions = "121",
//                        rating = "03"
//                    ),
//                    AssayUI(
//                        id = 5,
//                        name = "Методика диагностики самооценки психических состояний\"\n",
//                        idCategory = 15,
//                        nameCategory = "Психическое состояние",
//                        countQuestions = "121",
//                        rating = "03"
//                    ),
//                    AssayUI(
//                        id = 6,
//                        name = "Методика диагностики самооценки психических состояний\"\n",
//                        idCategory = 6,
//                        nameCategory = "Психическое состояние",
//                        countQuestions = "121",
//                        rating = "03"
//                    ),
//                    AssayUI(
//                        id = 7,
//                        name = "Методика диагностики самооценки психических состояний\"\n",
//                        idCategory = 7,
//                        nameCategory = "Психическое состояние",
//                        countQuestions = "121",
//                        rating = "03"
//                    ),
//                    AssayUI(
//                        id = 8,
//                        name = "Методика диагностики самооценки психических состояний\"\n",
//                        idCategory = 8,
//                        nameCategory = "Психическое состояние",
//                        countQuestions = "121",
//                        rating = "03"
//                    ),
//                    AssayUI(
//                        id = 9,
//                        name = "Методика диагностики самооценки психических состояний\"\n",
//                        idCategory = 11231,
//                        nameCategory = "Психическое состояние",
//                        countQuestions = "121",
//                        rating = "03"
//                    ),
//                ).toImmutableList(),
//                uiState = UIState.SUCCESS
//            )
//        )
//    }
//    DecideTheme {
//        Column(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            AssayMainScreenScaffold(
//                state = state,
//                onClickAssay = {},
//                onEvent = {}
//            )
//        }
//    }
//}
