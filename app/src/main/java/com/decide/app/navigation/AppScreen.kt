package com.decide.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AppScreen(
    startDestination: String, auth: String? = null
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val itemsNavBottomBar = listOf(
        AssayList.route, AssayCategoriesList.route, Profile.route
    )
    NavigationBar {
        itemsNavBottomBar.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { selectedItem = index },
                icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = item) },
                label = {
                    Text(
                        text = item
                    )
                }
            )
        }
    }
}