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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.card.CardAssay
import com.decide.uikit.ui.searchBar.SearchBarDecide

@Composable
fun AssayMainScreen(
    viewModel: AssayMainViewModel = hiltViewModel(),
    onClickAssay: (argument: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (state) {
        is StateAssay.Error -> TODO()
        StateAssay.None -> {
            AssayMainContent(
                assays = emptyList(),
                onClickAssay = {},
            )
        }

        is StateAssay.Success -> {

            AssayMainContent(
                assays = (state as StateAssay.Success).assays,
                onClickAssay = onClickAssay,
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AssayMainContent(
    @PreviewParameter(AssaysPreviewProvider::class, limit = 1) assays: List<AssayUI>,
    onChangeSearch: (text: String) -> Unit = {},
    onClickAssay: (argument: Int) -> Unit,
) {
    val textSearch by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier//надо передавать сюда сверху
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
        LazyColumn {
            itemsIndexed(assays) { index, assay ->
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
                        Log.d("TAG", "index.toString() = " + index.toString())
//                        temp(index)
                        onClickAssay(index)
                    }
                )
            }
        }
    }
}

private class AssaysPreviewProvider : PreviewParameterProvider<List<AssayUI>> {
    private val assayPreviewProvider = AssayPreviewProvider()
    override val values: Sequence<List<AssayUI>> =
        sequenceOf(
            assayPreviewProvider.values
                .toList()
        )
}

private class AssayPreviewProvider : PreviewParameterProvider<AssayUI> {
    override val values =
        sequenceOf(
            AssayUI(
                id = 1,
                name = "Which of the following professional development \n",
                nameCategory = "Graphic Design",
                countQuestions = "31",
                rating = "123"
            ),
            AssayUI(
                id = 2,
                name = "Graphic Design Advanced",
                nameCategory = "Отклонения",
                countQuestions = "31",
                rating = "123"
            ),
            AssayUI(
                id = 3,
                name = "Which of the following professional development",
                nameCategory = "Депрессия",
                countQuestions = "31",
                rating = "123"
            )
        )
}