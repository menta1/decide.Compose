package com.decide.uikit.ui.card

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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.AsyncImage
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme

@Composable
fun CardAssay(
    modifier: Modifier = Modifier,
    image: Int,
    icon: Painter,
    textCategory: String,
    textColorCategory: Color = DecideTheme.colors.accentPink,
    textStyleCategory: TextStyle = DecideTheme.typography.bodySmall,
    textAssay: String,
    textColorAssay: Color = DecideTheme.colors.text,
    textStyleAssay: TextStyle = DecideTheme.typography.bodyMedium,
    textRating: String = "0.0",
    textColorRating: Color = DecideTheme.colors.text,
    textStyleRating: TextStyle = DecideTheme.typography.labelSmall,
    textQuestion: String,
    textColorQuestion: Color = DecideTheme.colors.text,
    textStyleQuestion: TextStyle = DecideTheme.typography.labelMedium,
    onClickAssay: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClickAssay() }
            .clip(RoundedCornerShape(16.dp))
            .background(DecideTheme.colors.container),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier.size(100.dp)
        ) {
            AsyncImage(
                model = image,
                contentScale = ContentScale.Crop,
                contentDescription = "image assay"
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.padding(top = 4.dp)) {
                Text(
                    text = textAssay,
                    color = textColorAssay,
                    style = textStyleAssay,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = textCategory,
                    color = textColorCategory,
                    style = textStyleCategory,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
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

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 8888288)
@Composable
fun PreviewCardAssay() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        CardAssay(
            image = R.drawable.category_capabilities,
            icon = painterResource(id = R.drawable.ic_star_rating),
            textAssay = "Which of the following professional development Which of the following professional development",
            textCategory = "Интеллект",
            textQuestion = "24 вопроса",
            textRating = "4.2",
            onClickAssay = {}
        )
    }
}