package com.decide.app.feature.assay.assayMain.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.searchBar.SearchBarDecide

@Composable
fun ScrollableAppBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    background: Color = DecideTheme.colors.background,
    scrollUpState: Boolean,
) {
    val position by animateFloatAsState(if (scrollUpState) -200f else 0f, label = "")

//    Surface(
//        modifier = Modifier.graphicsLayer { translationY = (position) },
////        tonalElevation = 8.dp,
////        shadowElevation = 8.dp
////        elevation = 8.dp
//    ) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { translationY = (position) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (navigationIcon != null) {
                navigationIcon()
            }
            Text(
                text = "Психологические тесты",
                style = DecideTheme.typography.titleLarge,
                color = DecideTheme.colors.textReverse
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchBarDecide(
                value = value,
                onValueChange = onValueChange,
            )
        }
    }


//    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewScrollableAppBar() {
    DecideTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ScrollableAppBar(
                value = "state.searchText",
                onValueChange = { },
                scrollUpState = false
            )

        }
    }
}