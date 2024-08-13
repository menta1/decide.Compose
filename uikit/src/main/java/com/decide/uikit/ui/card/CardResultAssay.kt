package com.decide.uikit.ui.card

import androidx.compose.foundation.Image
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme

@Composable
fun CardResultAssay(
    modifier: Modifier = Modifier,
    image: Painter,
    textCategory: String,
    textColorCategory: Color = DecideTheme.colors.inputBlack,
    textStyleCategory: TextStyle = DecideTheme.typography.tabBar,
    textAssay: String,
    textColorAssay: Color = DecideTheme.colors.inputBlack,
    textStyleAssay: TextStyle = DecideTheme.typography.cardLarge,
    textDate: String,
    textColorDate: Color = DecideTheme.colors.inputBlack,
    textStyleDate: TextStyle = DecideTheme.typography.tabBarSelected,
    results: String,
    onClickResults: () -> Unit,
    onClickResult: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClickResults() }
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = image, contentDescription = ""
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(
            modifier = Modifier
                .padding(top = 14.dp)
                .padding(end = 14.dp),

            ) {
            Text(
                text = textCategory,
                color = textColorCategory,
                style = textStyleCategory,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = textAssay,
                color = textColorAssay,
                style = textStyleAssay,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = textDate,
                    color = textColorDate,
                    style = textStyleDate,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Box(){
                    Text(
                        text = "РЕЗУЛЬТАТЫ",
                        color = DecideTheme.colors.accentGreen,
                        style = textStyleAssay,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textDecoration = TextDecoration.Underline
                    )

                    DropdownMenu(expanded = true, onDismissRequest = { /*TODO*/ }) {

                        DropdownMenuItem(text = {
                            Text(text = results)
                        }, onClick = { onClickResult() })
                    }
                }

            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCardResultAssay() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            CardResultAssay(
                image = painterResource(id = R.drawable.category_capabilities),
                textCategory = "Graphic Design",
                textAssay = "Graphic Design Advanced",
                textDate = "14.07.2024",
                results = "Xnj njsdf",
                onClickResults = {},
                onClickResult = {}
            )
        }
    }
}