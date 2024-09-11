package com.decide.uikit.ui.statistic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.decide.uikit.theme.DecideTheme
import kotlinx.coroutines.delay

@Composable
fun PieStatistic(
    data: Map<String, Int>,
    radiusOuter: Dp = 60.dp,
    chartBarWidth: Dp = 25.dp,
    animDuration: Int = 500,
    delayTime: Long = 0,
    itemsStyle: TextStyle = DecideTheme.typography.labelLarge,
    itemsColor: Color = DecideTheme.colors.inputBlack,
    itemsPercentStyle: TextStyle = DecideTheme.typography.labelMedium,
    itemsPercentColor: Color = DecideTheme.colors.gray,
) {

    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    val colors = listOf(
        Color(0xFFFF495F),
        Color(0xFFFFB038),
        Color(0xFF5EE173),
        Color(0xFF3A82EF),
    )

    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = ""
    )


    LaunchedEffect(key1 = true) {
        delay(delayTime)
        animationPlayed = true
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colors[index],
                        startAngle = lastValue,
                        sweepAngle = value,
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
                    lastValue += value
                }
            }
        }

        DetailsPieChart(
            data = data,
            colors = colors,
            animate = animationPlayed,
            itemsStyle = itemsStyle,
            itemsColor = itemsColor,
            itemsPercentStyle = itemsPercentStyle,
            itemsPercentColor = itemsPercentColor
        )

    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DetailsPieChart(
    data: Map<String, Int>,
    colors: List<Color>,
    animate: Boolean = false,
    itemsStyle: TextStyle,
    itemsColor: Color,
    itemsPercentStyle: TextStyle,
    itemsPercentColor: Color,
) {

    AnimatedVisibility(
        visible = animate,
        enter = fadeIn(),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateEnterExit(
                    enter = slideInHorizontally(
                        initialOffsetX = { it }
                    ),
                )
        ) {
            data.values.forEachIndexed { index, value ->
                DetailsPieChartItem(
                    data = Pair(data.keys.elementAt(index), value),
                    color = colors[index],
                    itemsStyle = itemsStyle,
                    itemsColor = itemsColor,
                    itemsPercentStyle = itemsPercentStyle,
                    itemsPercentColor = itemsPercentColor
                )
            }

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
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 20.dp),
        color = Color.Transparent
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = color,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(height)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
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
        // Preview with sample data
        PieStatistic(
            data = mapOf(
                Pair("Холерик", 30),
                Pair("Сангвиник", 20),
                Pair("Флегматик", 40),
                Pair("Меланхолик", 10),
            )
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewhartItem() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailsPieChartItem() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
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

