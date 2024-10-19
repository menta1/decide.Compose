package com.decide.uikit.ui.statistic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.statistic.modal.TemperamentUI

@Composable
fun PieStatistic(
    data: TemperamentUI,
    radiusOuter: Dp = 60.dp,
    chartBarWidth: Dp = 25.dp,
    animDuration: Int = 500,
    delayTime: Long = 0,
    itemsStyle: TextStyle = DecideTheme.typography.labelLarge,
    itemsColor: Color = DecideTheme.colors.inputBlack,
    itemsPercentStyle: TextStyle = DecideTheme.typography.labelMedium,
    itemsPercentColor: Color = DecideTheme.colors.gray,
) {

    val totalSum =
        data.choleric.second + data.melancholic.second + data.sanguine.second + data.phlegmatic.second
    val floatValue = mutableListOf<Pair<String, Float>>()

    floatValue.addAll(
        listOf(
            Pair(
                first = data.choleric.first,
                second = data.choleric.second.toFloat()
            ),
            Pair(
                first = data.melancholic.first,
                second = data.melancholic.second.toFloat()
            ),
            Pair(
                first = data.sanguine.first,
                second = data.sanguine.second.toFloat()
            ),
            Pair(
                first = data.phlegmatic.first,
                second = data.phlegmatic.second.toFloat()
            ),
        )
    )

    val colors = listOf(
        Color(0xFFFF495F),
        Color(0xFFFFB038),
        Color(0xFF5EE173),
        Color(0xFF3A82EF),
    )

    var lastValue = 0f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier.size(120.dp), contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.size(radiusOuter * 2f)
            ) {
                floatValue.forEachIndexed { index, value ->
                    val valueItem = 360 * value.second / totalSum.toFloat()
                    drawArc(
                        color = colors[index],
                        startAngle = lastValue,
                        sweepAngle = valueItem,
                        useCenter = false,
                        style = Stroke(
                            chartBarWidth.toPx(),
                            cap = StrokeCap.Butt,
                        )
                    )
                    drawArc(
                        color = Color.Transparent,
                        startAngle = lastValue,
                        sweepAngle = 4f,
                        useCenter = false,
                        style = Stroke(
                            chartBarWidth.toPx(),
                            cap = StrokeCap.Butt,
                        )
                    )
                    lastValue += valueItem
                }
            }
        }

        DetailsPieChart(
            data = floatValue,
            colors = colors,
            itemsStyle = itemsStyle,
            itemsColor = itemsColor,
            itemsPercentStyle = itemsPercentStyle,
            itemsPercentColor = itemsPercentColor
        )

    }

}

@Composable
fun DetailsPieChart(
    data: List<Pair<String, Float>>,
    colors: List<Color>,
    animate: Boolean = false,
    itemsStyle: TextStyle,
    itemsColor: Color,
    itemsPercentStyle: TextStyle,
    itemsPercentColor: Color,
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        data.forEachIndexed { index, pair ->
            DetailsPieChartItem(
                data = Pair(pair.first, pair.second.toInt()),
                color = colors[index],
                itemsStyle = itemsStyle,
                itemsColor = itemsColor,
                itemsPercentStyle = itemsPercentStyle,
                itemsPercentColor = itemsPercentColor
            )
        }

    }

}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, Int>,
    height: Dp = 25.dp,
    color: Color,
    itemsStyle: TextStyle,
    itemsColor: Color,
    itemsPercentStyle: TextStyle,
    itemsPercentColor: Color,
) {

    Surface(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 20.dp), color = Color.Transparent
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = color, shape = RoundedCornerShape(10.dp)
                    )
                    .size(height)
            )

            Column(
                modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.first,
                    style = itemsStyle,
                    color = itemsColor
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.second.toString() + "%",
                    style = itemsPercentStyle,
                    color = itemsPercentColor
                )
            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center
    ) {
        PieStatistic(
            data = TemperamentUI(
                choleric = Pair(first = "Холерик", second = 25.0),
                sanguine = Pair(first = "Сангвиник", second = 25.0),
                melancholic = Pair(first = "Меланхолик", second = 25.0),
                phlegmatic = Pair(first = "Флегматик", second = 25.0)
            ),
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailsPieChartItem() {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
    ) {
        DetailsPieChartItem(
            data = Pair("Холерик", 30),
            color = DecideTheme.colors.accentPink,
            itemsStyle = DecideTheme.typography.labelLarge,
            itemsColor = DecideTheme.colors.inputBlack,
            itemsPercentStyle = DecideTheme.typography.labelMedium,
            itemsPercentColor = DecideTheme.colors.gray,
        )

    }
}

