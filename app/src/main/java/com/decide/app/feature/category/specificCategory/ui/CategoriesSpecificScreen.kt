package com.decide.app.feature.category.specificCategory.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.decide.app.feature.assay.assayMain.ui.AssayUI
import com.decide.app.utils.setDrawable
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.ErrorMessage
import com.decide.uikit.ui.buttons.ButtonBackArrow
import com.decide.uikit.ui.buttons.CircleDecideIndicator
import com.decide.uikit.ui.card.CardAssay

@Composable
fun CategoriesSpecificScreen(
    modifier: Modifier = Modifier,
    onClickAssay: (id: Int) -> Unit,
    onClickBack: () -> Unit
) {

    val viewModel: CategoriesSpecificViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    CategoriesSpecificScreen(
        modifier = modifier,
        state = state,
        onClickAssay = onClickAssay,
        onClickBack = onClickBack
    )
}

@Composable
fun CategoriesSpecificScreen(
    modifier: Modifier = Modifier,
    state: CategoriesSpecificState,
    onClickAssay: (id: Int) -> Unit,
    onClickBack: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DecideTheme.colors.background)
            .padding(top = 36.dp)
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickBack() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonBackArrow(text = "Назад", onClick = { onClickBack() })
        }
        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is CategoriesSpecificState.Loaded -> {
                Text(
                    text = state.description,
                    style = DecideTheme.typography.labelLarge,
                    color = DecideTheme.colors.inputBlack,
                )
                LazyColumn {
                    items(state.assays) { assay ->
                        Spacer(modifier = Modifier.height(16.dp))
                        CardAssay(
                            image = painterResource(id = setDrawable(assay.idCategory)),
                            icon = painterResource(id = R.drawable.ic_star_rating),
                            textCategory = assay.nameCategory,
                            textAssay = assay.name,
                            textRating = assay.rating,
                            textQuestion = pluralStringResource(
                                id = com.decide.app.R.plurals.questions,
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

            CategoriesSpecificState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircleDecideIndicator()
                }
            }

            is CategoriesSpecificState.Error -> {
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


@Preview
@Composable
fun PreviewCategoriesSpecificScreen() {
    val state: CategoriesSpecificState by remember {
        mutableStateOf(
            CategoriesSpecificState.Loaded(
                assays = listOf(
                    AssayUI(
                        id = 1,
                        name = "Оценка социальной неудовлетворенности",
                        nameCategory = "Психическое состояние",
                        idCategory = 1,
                        countQuestions = "44",
                        rating = "2.3"
                    ),
                    AssayUI(
                        id = 2,
                        name = "Методика диагностики самооценки психических состояний",
                        nameCategory = "Психическое состояние",
                        idCategory = 2,
                        countQuestions = "423",
                        rating = "4.3"
                    ),
                    AssayUI(
                        id = 3,
                        name = "Торонтская алекситимическая шкала",
                        nameCategory = "Свойства личности",
                        idCategory = 3,
                        countQuestions = "144",
                        rating = "2.33"
                    ),
                ),
                description = "Оценка социальной неудовлетворенности"
            )
        )
    }
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            CategoriesSpecificScreen(state = state, onClickAssay = {}, onClickBack = {})
        }
    }
}

@Preview
@Composable
fun PreviewErrorCategoriesSpecificScreen() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            CategoriesSpecificScreen(
                state = CategoriesSpecificState.Error(""),
                onClickAssay = {},
                onClickBack = {})
        }
    }
}

@Preview
@Composable
fun PreviewLoadingCategoriesSpecificScreen() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            CategoriesSpecificScreen(
                state = CategoriesSpecificState.Loading,
                onClickAssay = {},
                onClickBack = {})
        }
    }
}

