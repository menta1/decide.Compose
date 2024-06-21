package com.decide.uikit.ui.navigation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decide.uikit.R
import com.decide.uikit.theme.DecideTheme


@Composable
fun NavBottomBar(
    modifier: Modifier = Modifier,
    routeAssay: String = "",
    routeCategory: String = "",
    routePassed: String = "",
    routeProfile: String = "",
    currentRoute: String? = "",

    assayItemText: String = stringResource(id = R.string.bottom_bar_nav_assays),
    categoryItemText: String = stringResource(id = R.string.bottom_bar_nav_categories),
    passedItemText: String = stringResource(id = R.string.bottom_bar_nav_passes),
    profileItemText: String = stringResource(id = R.string.bottom_bar_nav_profile),

    assayItemImage: Painter = painterResource(id = R.drawable.ic_bottom_bar_assay),
    categoryItemImage: Painter = painterResource(id = R.drawable.ic_bottom_bar_category),
    passedItemImage: Painter = painterResource(id = R.drawable.ic_bottom_bar_passed),
    profileItemImage: Painter = painterResource(id = R.drawable.ic_bottom_bar_profile),

    itemClickAssay: () -> Unit = {},
    itemClickCategory: () -> Unit = {},
    itemClickPassed: () -> Unit = {},
    itemClickProfile: () -> Unit = {},

    ) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(DecideTheme.colors.inputWhite),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        BottomBarItem(
            selected = routeAssay == currentRoute,
            onClick = {
                itemClickAssay.invoke()
            },
            text = assayItemText,
            painter = assayItemImage,
        )
        BottomBarItem(
            selected = routeCategory == currentRoute,
            onClick = {
                itemClickCategory.invoke()
            },
            text = categoryItemText,
            painter = categoryItemImage,
        )
        BottomBarItem(
            selected = routePassed == currentRoute,
            onClick = {
                if (routePassed != currentRoute)
                itemClickPassed.invoke()
            },
            text = passedItemText,
            painter = passedItemImage,
        )
        BottomBarItem(
            selected = routeProfile == currentRoute,
            onClick = {
                itemClickProfile.invoke()
            },
            text = profileItemText,
            painter = profileItemImage,
        )
    }
}

@Composable
fun BottomBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit,
    text: String,
    painter: Painter,
) {
    Column(
        modifier = modifier
            .width(75.dp)
            .height(56.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    onClick.invoke()
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (selected) Arrangement.Center else Arrangement.Bottom
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = DecideTheme.colors.inputBlack,
            modifier = modifier.padding(bottom = 2.dp)
        )
        Text(
            text = text,
            color = DecideTheme.colors.inputBlack,
            style = if (selected) DecideTheme.typography.tabBarSelected else DecideTheme.typography.tabBar,
            modifier = Modifier.padding(bottom = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 8888288)
@Composable
fun PreviewNavBottomBarItem() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            BottomBarItem(
                selected = true,
                onClick = {

                },
                text = "Категории",
                painter = painterResource(id = R.drawable.ic_bottom_bar_category),
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 8888288)
@Composable
fun PreviewNavBottomBar() {
    DecideTheme {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            NavBottomBar()
        }
    }
}
