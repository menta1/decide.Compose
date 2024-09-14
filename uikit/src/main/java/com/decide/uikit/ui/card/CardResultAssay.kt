package com.decide.uikit.ui.card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastJoinToString
import com.decide.uikit.theme.DecideTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CardResultAssay(
    modifier: Modifier = Modifier,
    id: Int,
    expandedItemId: Int,
    backgroundColor: Color = DecideTheme.colors.inputWhite,
    textCategory: String,
    textColorCategory: Color = DecideTheme.colors.inputBlack,
    textStyleCategory: TextStyle = DecideTheme.typography.labelMedium,
    textAssay: String,
    textColorAssay: Color = DecideTheme.colors.inputBlack,
    textStyleAssay: TextStyle = DecideTheme.typography.titleMedium,
    textDate: String,
    listDateResults: List<Pair<Long, List<String>>>,
    textColorDate: Color = DecideTheme.colors.inputBlack,
    textStyleDate: TextStyle = DecideTheme.typography.titleMedium,
    onClickResult: (id: Int) -> Unit,
    onShowDetailResult: (date: Long) -> Unit
) {

    val rotation = animateFloatAsState(
        targetValue = if (id == expandedItemId) 180f else 0f,
        label = ""
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClickResult(id) },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = backgroundColor
        )
    ) {
        Spacer(modifier = Modifier.width(14.dp))
        Column(
            modifier = Modifier
                .padding(top = 14.dp)
                .padding(horizontal = 14.dp),
        ) {
            Text(
                text = textCategory,
                color = textColorCategory,
                style = textStyleCategory,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = textAssay,
                color = textColorAssay,
                style = textStyleAssay,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = CenterVertically
            ) {

                Text(
                    text = "Последний результат от ",
                    color = textColorDate,
                    style = DecideTheme.typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = textDate,
                    color = textColorDate,
                    style = DecideTheme.typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Image(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier
                        .align(CenterVertically)
                        .graphicsLayer(
                            rotationZ = rotation.value
                        )
                )
            }
        }
        AnimatedVisibility(visible = id == expandedItemId) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .padding(vertical = 8.dp)
            ) {
                listDateResults.asReversed().forEachIndexed { index, item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                            .clickable { onShowDetailResult(item.first) },
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(1.dp, Color.Gray),
                        colors = CardDefaults.cardColors()
                            .copy(containerColor = backgroundColor)
                    ) {
                        Column(
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = "Результат: "
                                        + item.second.fastJoinToString(),
                                color = textColorDate,
                                style = textStyleDate,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            Row {
                                Text(
                                    text = "Дата прохождения: ",
                                    color = textColorDate,
                                    style = textStyleDate,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = dateFormatter(item.first),
                                    color = textColorDate,
                                    style = textStyleDate,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun dateFormatter(date: Long): String {
    val dates = Date(date)
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return sdf.format(dates)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCardResultAssay() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            CardResultAssay(textCategory = "Graphic Design",
                id = 1,
                expandedItemId = 1,
                listDateResults = listOf(
                    Pair(
                        123123132123,
                        listOf(
                            "Result",
                            "Result",
                            "Result",
                            "Result",
                            "Result",
                            "Result",
                            "Result",
                            "Result",
                            "Result",
                            "Result",
                            "Result",
                        )
                    ),
                    Pair(
                        123123132123, listOf("Result")
                    ),
                    Pair(
                        123123132123, listOf("Result")
                    ),
                ),
                textAssay = "Graphic Design Advanced",
                textDate = "14.07.2024",
                onClickResult = {},
                onShowDetailResult = {})
        }
    }
}