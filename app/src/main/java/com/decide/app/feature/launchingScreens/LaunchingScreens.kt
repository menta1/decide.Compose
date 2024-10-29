package com.decide.app.feature.launchingScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.decide.app.R
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.buttons.ButtonCircle
import com.decide.uikit.ui.buttons.ButtonMain
import kotlinx.coroutines.launch

@Composable
fun LaunchingScreens(
    skip: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = {
        3
    })
    val animationScope = rememberCoroutineScope()

    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
        when (page) {
            1 -> {
                PageScreen(
                    skip = { skip() },
                    next = {
                        animationScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    title = "Открой себя",
                    body = "Психологические тесты помогут тебе глубже понять свою личность и эмоции",
                    pageCount = pagerState.pageCount,
                    currentPage = pagerState.currentPage
                )
            }

            2 -> {
                PageScreen(
                    skip = { skip() },
                    next = {
                        animationScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    title = "Следи за прогрессом",
                    body = "Анализируй изменения своих результатов и наблюдай за развитием",
                    pageCount = pagerState.pageCount,
                    currentPage = pagerState.currentPage
                )
            }

            else -> {
                PageScreen(
                    skip = { skip() },
                    next = {
                        animationScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    title = "Сравнивай с другими",
                    body = "Исследуй общую статистику своих тестов с другими и открывай новые грани своей личности",
                    pageCount = pagerState.pageCount,
                    currentPage = pagerState.currentPage
                )
            }
        }
    }
}

@Composable
private fun PageScreen(
    skip: () -> Unit,
    next: () -> Unit,
    title: String,
    body: String,
    pageCount: Int,
    currentPage: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DecideTheme.colors.background)
            .padding(top = 24.dp)
            .padding(bottom = 34.dp)
            .padding(horizontal = 34.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(0.4f)
        ) {
            if (currentPage + 1 != pageCount) {
                Text(
                    modifier = Modifier
                        .clickable {
                            skip()
                        },
                    text = "Пропустить",
                    style = DecideTheme.typography.titleSmall,
                    color = DecideTheme.colors.text
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.size(250.dp),
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    style = DecideTheme.typography.titleLarge,
                    color = DecideTheme.colors.text
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = body,
                    style = DecideTheme.typography.bodyLarge,
                    color = DecideTheme.colors.unFocused,
                    textAlign = TextAlign.Center
                )
            }

        }

        Row(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                Modifier
                    .wrapContentHeight()
                    .padding(bottom = 8.dp)
                    .weight(0.6f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Bottom
            ) {
                repeat(pageCount) { iteration ->
                    val color =
                        if (currentPage == iteration) DecideTheme.colors.text else DecideTheme.colors.unFocused
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(16.dp)
                    )
                }
            }

            if (currentPage + 1 == pageCount) {
                ButtonMain(
                    modifier = Modifier.weight(0.4f),
                    text = "Начать"
                ) { skip() }
            } else {
                ButtonCircle { next() }
            }

        }

    }
}

@MainPreview
@Composable
private fun Preview() {
    DecideTheme {
        LaunchingScreens { }
    }
}

