package com.decide.uikit.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.decide.uikit.R
import com.decide.uikit.common.MainPreview
import com.decide.uikit.theme.DecideTheme

@Composable
fun CardAssay(
    modifier: Modifier = Modifier,
    image: Int,
    icon: Painter,
    textCategory: String,
    idCategory: Int,
    textColorCategory: Color = DecideTheme.colors.accentPink,
    textStyleCategory: TextStyle = DecideTheme.typography.titleSmall,
    textAssay: String,
    textColorAssay: Color = DecideTheme.colors.text,
    textStyleAssay: TextStyle = DecideTheme.typography.titleSmall,
    textRating: String = "0.0",
    textColorRating: Color = DecideTheme.colors.text,
    textStyleRating: TextStyle = DecideTheme.typography.labelSmall,
    textQuestion: String,
    textColorQuestion: Color = DecideTheme.colors.unFocused,
    textStyleQuestion: TextStyle = DecideTheme.typography.labelSmall,
    onClickAssay: () -> Unit,
) {
    var height by remember { mutableIntStateOf(0) }
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable { onClickAssay() }
        .clip(RoundedCornerShape(16.dp))
        .background(DecideTheme.colors.container),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically) {

        AsyncImage(
            modifier = Modifier
                .size(height = 100.dp, width = 100.dp)
                .padding(4.dp)
                .clip(RoundedCornerShape(16.dp)),
            model = image,
            contentScale = ContentScale.Crop,
            contentDescription = "image assay"
        )

        Column(
            modifier = Modifier
                .onSizeChanged { size ->
                    height = size.height
                }
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.padding(top = 4.dp)) {
                Text(
                    text = textAssay, color = textColorAssay, style = textStyleAssay
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = textCategory,
                    color = setColorCategory(idCategory),
                    style = textStyleCategory,
                    maxLines = 1
                )
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = textQuestion, color = textColorQuestion, style = textStyleQuestion)
            }
        }
    }
}

@Composable
private fun setColorCategory(idCategory: Int): Color {
    return when (idCategory) {
        1 -> {
            DecideTheme.colors.category1
        }

        2 -> {
            DecideTheme.colors.category2
        }

        3 -> {
            DecideTheme.colors.category3
        }

        4 -> {
            DecideTheme.colors.category4
        }

        5 -> {
            DecideTheme.colors.category5
        }

        6 -> {
            DecideTheme.colors.category6
        }

        7 -> {
            DecideTheme.colors.category7
        }

        8 -> {
            DecideTheme.colors.category8
        }

        9 -> {
            DecideTheme.colors.category9
        }

        10 -> {
            DecideTheme.colors.category10
        }

        else -> {
            DecideTheme.colors.category11
        }
    }
}

@MainPreview
@Composable
fun PreviewCardAssay() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            CardAssay(
                image = R.drawable.category_capabilities,
                icon = painterResource(id = R.drawable.ic_star_rating),
                textAssay = "Which of the following professional development Which of the following professional development",
                textCategory = "Интеллект",
                textQuestion = "24 вопроса",
                textRating = "4.2",
                onClickAssay = {},
                idCategory = 1
            )
        }
    }
}