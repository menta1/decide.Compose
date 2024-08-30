package com.decide.uikit.ui.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme

//Надо доделать, почему то закладка не отображается
//Название темы почему то занимает всю высоту
@Composable
fun CardAssay(
    modifier: Modifier = Modifier,
    onClickBookmark: () -> Unit = {},
    image: Painter,
    icon: Painter,
    textCategory: String,
    textColorCategory: Color = DecideTheme.colors.accentPink,
    textStyleCategory: TextStyle = DecideTheme.typography.bodyLarge,
    textAssay: String,
    textColorAssay: Color = DecideTheme.colors.inputBlack,
    textStyleAssay: TextStyle = DecideTheme.typography.titleMedium,
    textRating: String,
    textColorRating: Color = DecideTheme.colors.inputBlack,
    textStyleRating: TextStyle = DecideTheme.typography.labelSmall,
    textQuestion: String,
    textColorQuestion: Color = DecideTheme.colors.inputBlack,
    textStyleQuestion: TextStyle = DecideTheme.typography.labelMedium,
    isBookmark: Boolean = false,
    isNewAssay: Boolean = false,
    onClickAssay: () -> Unit,
) {
    Row(
        modifier = modifier
//            .height(100.dp)
            .fillMaxWidth()
            .clickable { onClickAssay() }
            .clip(RoundedCornerShape(16.dp))
            .background(DecideTheme.colors.inputWhite),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = modifier.size(100.dp)
        ) {
            Image(
                painter = image,
                contentScale = ContentScale.Crop,
                contentDescription = "image assay"
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
//                    .fillMaxHeight()
                    .padding(start = 10.dp)
//                    .weight(5f),
                , verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.padding(top = 4.dp)) {
                    Text(
                        text = textCategory,
                        color = textColorCategory,
                        style = textStyleCategory,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = textAssay,
                        color = textColorAssay,
                        style = textStyleAssay,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                }
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = "icon",
                        tint = DecideTheme.colors.accentYellow
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = textRating, color = textColorRating, style = textStyleRating)

                    Icon(
                        painter = painterResource(id = R.drawable.divider_small),
                        contentDescription = ""
                    )
                    Text(text = textQuestion, color = textColorQuestion, style = textStyleQuestion)
                }
            }
//            Column(modifier = Modifier.weight(1f)) {
//                IconButton(onClick = { onClickBookmark() }) {
//                    Icon(
//                        painter = if (isBookmark) painterResource(id = R.drawable.ic_bookmark_selected)
//                        else painterResource(
//                            id = R.drawable.ic_bookmark_unselected
//                        ), contentDescription = "bookmark"
//                    )
//                }
//                if (isNewAssay) {
//                    Text(text = "New", color = DecideTheme.colors.textGreen, style = textStyleAssay)
//                }
//            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 8888288)
@Composable
fun PreviewCardAssay() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        CardAssay(
            image = painterResource(id = R.drawable.category_capabilities),
            icon = painterResource(id = R.drawable.ic_star_rating),
            textAssay = "Which of the following professional development",
            textCategory = "Интеллект",
            textQuestion = "24 вопроса",
            textRating = "4.2",
            onClickAssay = {}
        )
    }
}