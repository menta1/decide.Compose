package com.decide.uikit.ui.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.decide.uikit.theme.DecideTheme

@Composable
fun CardCategory(
    modifier: Modifier = Modifier,
    imageId: Int,
    textCategory: String,
    textColorCategory: Color = DecideTheme.colors.inputWhite,
    textStyleCategory: TextStyle = DecideTheme.typography.titleMedium,
    overlayColor: Color = DecideTheme.colors.inputBlack,
    overlayAlpha: Float = 0.3f,
    onClickAssay: () -> Unit,
) {
    Box(modifier = modifier
        .defaultMinSize(minWidth = 180.dp, minHeight = 180.dp)
        .clickable { onClickAssay() }
        .padding(4.dp)
        .clip(RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.BottomCenter) {
        Image(
            modifier = Modifier.drawWithContent {
                drawContent()
                drawRect(color = overlayColor.copy(alpha = overlayAlpha))
            },
            painter = painterResource(id = imageId),
            contentDescription = ""
        )
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = textCategory,
            color = textColorCategory,
            style = textStyleCategory,
            maxLines = 2,
            textAlign = TextAlign.Center
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewCardCategory() {
//    DecideTheme {
//        CardCategory(
//            imageId = painterResource(id = R.drawable.category_capabilities),
//            textCategory = "Способносrwerwer werwerwsdf sdf"
//        ) {
//
//        }
//    }
//}