package com.decide.uikit.ui.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme

@Composable
fun CardCategory(
    modifier: Modifier = Modifier,
    imageId: Int,
    textCategory: String,
    textColorCategory: Color = DecideTheme.colors.inputWhite,
    textStyleCategory: TextStyle = DecideTheme.typography.titleLarge,
    overlayColor: Color = DecideTheme.colors.inputBlack,
    overlayAlpha: Float = 0.4f,
    onClickAssay: () -> Unit,
) {
    Box(modifier = modifier
        .defaultMinSize(minWidth = 180.dp, minHeight = 180.dp)
        .clickable { onClickAssay() }
        .padding(4.dp)
        .clip(RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.BottomCenter) {
        AsyncImage(
            modifier = Modifier.drawWithContent {
                drawContent()
                drawRect(color = overlayColor.copy(alpha = overlayAlpha))
            },
            model = imageId,
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .padding(horizontal = 4.dp)
                .fillMaxWidth(),
            text = textCategory,
            color = textColorCategory,
            style = textStyleCategory,
            maxLines = 2,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardCategory() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            CardCategory(
                imageId = R.drawable.category_capabilities,
                textCategory = "Способносrwerwer werwerwsdf sdf"
            ) {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListCardCategory() {

    val list = listOf(
        Pair(first = R.drawable.category_capabilities, second = "Cgja;sdlkaasd"),
        Pair(first = R.drawable.category_capabilities, second = "sbhsasdfasdf"),
        Pair(first = R.drawable.category_capabilities, second = "adsfasdfcxzcv"),
        Pair(first = R.drawable.category_capabilities, second = "Cgjcvzx"),
        Pair(first = R.drawable.category_capabilities, second = "Cgjzxvaasd"),
        Pair(first = R.drawable.category_capabilities, second = "Cgja;xsd"),
        Pair(first = R.drawable.category_capabilities, second = "Cxd"),
        Pair(first = R.drawable.category_capabilities, second = "Cgja;sdfsafdsafdfsdlkaasd"),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            items(items = list) { item ->

                CardCategory(
                    imageId = item.first,
                    textCategory = item.second,
                    onClickAssay = { })
            }
        }
    }

}