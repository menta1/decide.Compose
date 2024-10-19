package com.decide.app.feature.category.mainCategory.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.feature.category.mainCategory.modals.Category
import com.decide.app.utils.setDrawable
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.ErrorMessage
import com.decide.uikit.ui.buttons.CircleDecideIndicator
import com.decide.uikit.ui.card.CardCategory

@Composable
fun CategoryScreen(
    onClickSpecificCategory: (id: Int) -> Unit
) {

    val viewModel: CategoryViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()


    CategoryScreen(
        state = state, onClickSpecificCategory = onClickSpecificCategory
    )

}

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    state: CategoryState,
    onClickSpecificCategory: (id: Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Категории",
            style = DecideTheme.typography.titleLarge,
            color = DecideTheme.colors.inputBlack
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is CategoryState.Loaded -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                    ) {
                        items(
                            items = state.categories,
                            key = { item -> item.id }) { item: Category ->
                            /**
                             * Нужно проверить содержит ли категория хотя бы один тест
                             * в противном случае не показывать
                             */
                            CardCategory(
                                imageId = setDrawable(item.id),
                                textCategory = item.name,
                                onClickAssay = {
                                    onClickSpecificCategory(
                                        item.id
                                    )
                                })
                        }
                    }
                }

                CategoryState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircleDecideIndicator()
                    }
                }


                is CategoryState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        ErrorMessage()
                    }
                }

                CategoryState.Empty -> {

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategoryScreen() {
    val state: CategoryState by remember {
        mutableStateOf(
            CategoryState.Loaded(
                listOf(
                    Category(
                        id = 1,
                        name = "Психическое состояние",
                        nameEng = "Mental condition",
                        colorBackground = "",
                        description = "",
                        countAssays = -1
                    ), Category(
                        id = 2,
                        name = "Свойства личности",
                        nameEng = "Personality properties",
                        colorBackground = "",
                        description = "",
                        countAssays = -1
                    ), Category(
                        id = 3,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = "",
                        countAssays = -1
                    ), Category(
                        id = 4,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = "",
                        countAssays = -1
                    ), Category(
                        id = 5,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = "",
                        countAssays = -1
                    ), Category(
                        id = 6,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = "",
                        countAssays = -1
                    ), Category(
                        id = 7,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = "",
                        countAssays = -1
                    ), Category(
                        id = 8,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = "",
                        countAssays = -1
                    ), Category(
                        id = 9,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = "",
                        countAssays = -1
                    ), Category(
                        id = 10,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = "",
                        countAssays = -1
                    ), Category(
                        id = 11,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = "",
                        countAssays = -1
                    ), Category(
                        id = 3,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = "",
                        countAssays = -1
                    ), Category(
                        id = 12,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = "",
                        countAssays = -1
                    )
                )
            )
        )
    }
    DecideTheme {
        CategoryScreen(onClickSpecificCategory = {}, state = state)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoadingCategoryScreen() {
    DecideTheme {
        CategoryScreen(onClickSpecificCategory = {}, state = CategoryState.Initial)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewErrorCategoryScreen() {
    DecideTheme {
        CategoryScreen(onClickSpecificCategory = {}, state = CategoryState.Error(""))
    }
}