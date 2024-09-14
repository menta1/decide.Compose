package com.decide.uikit.ui.statistic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decide.uikit.theme.DecideTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LineStatistic(
    modifier: Modifier = Modifier,
    height: Float = 60f,
    your: Float = 0f,
    all: Float = 0f,
    delayTime: Long = 0,
    itemsStyle: TextStyle = DecideTheme.typography.labelLarge,
    itemsColor: Color = DecideTheme.colors.inputBlack,
    itemsPercentStyle: TextStyle = DecideTheme.typography.labelMedium,
    itemsPercentColor: Color = DecideTheme.colors.gray,
    isHasResult: Boolean = false
) {

    var animationPlayed by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        delay(delayTime)
        animationPlayed = true
    }

    AnimatedVisibility(visible = animationPlayed) {
        Column(
            modifier = modifier
                .height(70.dp)
                .animateEnterExit(
                    enter = slideInHorizontally(
                        initialOffsetX = { it }
                    )
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            ) {
                val canvasWidth = size.width
                val yourValue = canvasWidth / 100 * your
                val allValue = canvasWidth / 100 * all

                drawLine(
                    start = Offset(x = 0.dp.toPx(), y = height / 2),
                    end = Offset(x = canvasWidth, y = height / 2),
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF3A82EF), Color(0xFFFF495F))
                    ),
                    strokeWidth = 10.dp.toPx(),
                    cap = StrokeCap.Round
                )

                if (yourValue == allValue) {
                    drawLine(
                        start = Offset(x = yourValue, y = (height / 2) - 35f),
                        end = Offset(x = yourValue, y = (height / 2) + 35f),
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFFFB038), Color(0xFF5EE173)),
                            tileMode = TileMode.Mirror
                        ),
                        strokeWidth = 5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                } else {
                    drawLine(
                        start = Offset(x = yourValue, y = (height / 2) - 30f),
                        end = Offset(x = yourValue, y = (height / 2) + 30f),
                        color = Color(0xFFFFB038),
                        strokeWidth = 5.dp.toPx(),
                        cap = StrokeCap.Round
                    )

                    drawLine(
                        start = Offset(x = allValue, y = (height / 2) - 30f),
                        end = Offset(x = allValue, y = (height / 2) + 30f),
                        color = Color(0xFF5EE173),
                        strokeWidth = 5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFFFB038),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .size(12.dp)
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = if (isHasResult) "Ваш" else "Нет данных",
                            style = itemsStyle,
                            color = itemsColor
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFF5EE173),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .size(12.dp)
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "Всех участников",
                            style = itemsStyle,
                            color = itemsColor
                        )
                    }

                }
                Column(
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFF3A82EF),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .size(12.dp)
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "Низкий уровень",
                            style = itemsStyle,
                            color = itemsColor
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFFF495F),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .size(12.dp)
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "Высокий уровень",
                            style = itemsStyle,
                            color = itemsColor
                        )
                    }

                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewLine() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Preview with sample data
        LineStatistic(
        )

    }
}