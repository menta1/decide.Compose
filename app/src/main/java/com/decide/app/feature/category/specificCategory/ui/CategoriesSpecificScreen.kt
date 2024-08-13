package com.decide.app.feature.category.specificCategory.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
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
import com.decide.app.feature.assay.mainAssay.ui.AssayUI
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme
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
            .background(DecideTheme.colors.mainBlue)
            .padding(top = 36.dp)
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Категории",
                style = DecideTheme.typography.titleScreen,
                color = DecideTheme.colors.inputBlack
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Right there immediately, literally in the palm of your hand, just no matter where you are on earth right now",
            style = DecideTheme.typography.cardLarge,
            color = DecideTheme.colors.inputBlack,
        )
        when (state) {
            is CategoriesSpecificState.Success -> {
                LazyColumn {
                    itemsIndexed(state.assays) { index, assay ->
                        Spacer(modifier = Modifier.height(16.dp))
                        CardAssay(
                            image = painterResource(id = R.drawable.category_capabilities),
                            icon = painterResource(id = R.drawable.ic_star_rating),
                            textCategory = assay.nameCategory,
                            textAssay = assay.name,
                            textRating = assay.rating,
                            textQuestion = assay.countQuestions,
                            onClickBookmark = {

                            },
                            onClickAssay = {
                                onClickAssay(index+1)
                            }
                        )
                    }
                }
            }

            CategoriesSpecificState.Default -> {}
            is CategoriesSpecificState.Error -> {}

        }
    }
}


@Preview
@Composable
fun PreviewCategoriesSpecificScreen() {
    val state: CategoriesSpecificState by remember {
        mutableStateOf(
            CategoriesSpecificState.Success(
                listOf(
                    AssayUI(
                        id = 1,
                        name = "Оценка социальной неудовлетворенности",
                        nameCategory = "Психическое состояние",
                        countQuestions = "44",
                        rating = "2.3"
                    ),
                    AssayUI(
                        id = 2,
                        name = "Методика диагностики самооценки психических состояний",
                        nameCategory = "Психическое состояние",
                        countQuestions = "423",
                        rating = "4.3"
                    ),
                    AssayUI(
                        id = 3,
                        name = "Торонтская алекситимическая шкала",
                        nameCategory = "Свойства личности",
                        countQuestions = "144",
                        rating = "2.33"
                    ),
                )
            )
        )
    }
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            CategoriesSpecificScreen(state = state, onClickAssay = {}, onClickBack = {})
        }
    }
}