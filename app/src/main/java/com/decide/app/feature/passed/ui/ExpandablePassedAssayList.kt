package com.decide.app.feature.passed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decide.app.feature.passed.models.PassedAssay
import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.app.utils.dateFormatter
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.card.CardResultAssay

@Composable
fun ExpandablePassedAssayList(
    list: List<PassedAssay>,
    onShowDetailResult: (Pair<Int, Long>) -> Unit
) {
    var clickedItemId by remember { mutableIntStateOf(Int.MIN_VALUE) }
    Box {
        LazyColumn {
            items(items = list, key = { assay -> assay.id }) { assay ->
                CardResultAssay(id = assay.id,
                    textCategory = assay.nameCategory,
                    textAssay = assay.name,
                    textDate = dateFormatter(assay.results.last().date.toString()),
                    onClickResult = { id ->
                        clickedItemId = if (clickedItemId == id) Int.MIN_VALUE
                        else id
                    },
                    listDateResults = assay.results.map { item ->
                        Pair(first = item.date, second = item.shortResults)
                    },
                    expandedItemId = clickedItemId,
                    onShowDetailResult = { date ->
                        onShowDetailResult(Pair(first = assay.id, second = date))
                    })
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpandablePassedAssayListPreview() {
    DecideTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        ) {
            ExpandablePassedAssayList(list = mockPassedAssay) {}
        }
    }
}

val mockPassedAssay = listOf(
    PassedAssay(
        id = 1,
        idCategory = 1,
        name = "Тест 1",
        nameCategory = "Категория 1",
        rating = "2.3",
        results = listOf(
            ResultCompletedAssay(
                date = 13287653264, shortResults = listOf("Good"), results = listOf("semiprecious")
            ),
            ResultCompletedAssay(
                date = 1324587653264, shortResults = listOf("Bad"), results = listOf("semiprecious")
            ),
            ResultCompletedAssay(
                date = 1328765323264,
                shortResults = listOf("Nice"),
                results = listOf("semiprecious")
            ),
            ResultCompletedAssay(
                date = 1328765323264,
                shortResults = listOf("Great"),
                results = listOf("semiprecious")
            ),
            ResultCompletedAssay(
                date = 13287653213264,
                shortResults = listOf("NotBad"),
                results = listOf("semiprecious")
            ),
        )
    )
)