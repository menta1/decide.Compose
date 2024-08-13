package com.decide.app.feature.category.mainCategory.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.app.R
import com.decide.app.feature.category.mainCategory.modals.Category
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.card.CardCategory

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    onClickSpecificCategory: (id: Int) -> Unit
) {

    val viewModel: CategoryViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()


    CategoryScreen(
        modifier = modifier,
        state = state,
        onClickSpecificCategory = onClickSpecificCategory
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
            .background(DecideTheme.colors.mainBlue)
            .padding(top = 36.dp)
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Категории",
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
                is CategoryState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 55.dp)
                    ) {
                        itemsIndexed(state.categories) { index: Int, item: Category ->

                            SortingCategories(
                                index,
                                item,
                                onClick = { onClickSpecificCategory(item.id) })
                        }
                    }
                }

                CategoryState.Default -> {

                }


                is CategoryState.Error -> {

                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCategoryScreen() {
    val state: CategoryState by remember {
        mutableStateOf(
            CategoryState.Success(
                listOf(
                    Category(
                        id = 1,
                        name = "Психическое состояние",
                        nameEng = "Mental condition",
                        colorBackground = "",
                        description = ""
                    ),
                    Category(
                        id = 2,
                        name = "Свойства личности",
                        nameEng = "Personality properties",
                        colorBackground = "",
                        description = ""
                    ),
                    Category(
                        id = 3,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = ""
                    ),
                    Category(
                        id = 3,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = ""
                    ),
                    Category(
                        id = 3,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = ""
                    ),
                    Category(
                        id = 3,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = ""
                    ),
                    Category(
                        id = 3,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = ""
                    ),
                    Category(
                        id = 3,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = ""
                    ),
                    Category(
                        id = 3,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = ""
                    ),
                    Category(
                        id = 3,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = ""
                    ),
                    Category(
                        id = 3,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = ""
                    ),
                    Category(
                        id = 3,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = ""
                    ),
                    Category(
                        id = 3,
                        name = "Темперамент",
                        nameEng = "Temperament",
                        colorBackground = "",
                        description = ""
                    )
                )
            )
        )
    }
    DecideTheme {
        CategoryScreen(onClickSpecificCategory = {}, state = state)
    }
}

@Composable
private fun SortingCategories(index: Int, item: Category, onClick: () -> Unit) {
    var painter: Painter = painterResource(id = R.drawable.category1)
    when (index) {
        1 -> {
            painter = painterResource(id = R.drawable.category1)
        }

        2 -> {
            painter = painterResource(id = R.drawable.category2)
        }

        3 -> {
            painter = painterResource(id = R.drawable.category3)
        }

        4 -> {
            painter = painterResource(id = R.drawable.category4)
        }

        5 -> {
            painter = painterResource(id = R.drawable.category5)
        }

        6 -> {
            painter = painterResource(id = R.drawable.category6)
        }

        7 -> {
            painter = painterResource(id = R.drawable.category7)
        }

        8 -> {
            painter = painterResource(id = R.drawable.category8)
        }

        9 -> {
            painter = painterResource(id = R.drawable.category9)
        }

        10 -> {
            painter = painterResource(id = R.drawable.category10)
        }

        11 -> {
            painter = painterResource(id = R.drawable.category11)
        }

        else -> {

        }
    }

    return CardCategory(
        image = painter,
        textCategory = item.name,
    ) { onClick() }
}