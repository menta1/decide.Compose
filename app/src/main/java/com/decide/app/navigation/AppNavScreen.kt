package com.decide.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.decide.app.navigation.navHost.AppHost
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.navigation.NavBottomBar


@Composable
fun AppNavScreen(
    startDestination: String,
    auth: String? = null
) {
    val itemsNavBottomBar = listOf(
        Assay.route, Category.route, Passed.route, Profile.route
    )
    val navController = rememberNavController()

    Scaffold(bottomBar = {
        if (itemsNavBottomBar.contains(
                navController.currentBackStackEntryAsState().value?.destination?.route
            )
        ) {
            BottomBar(navController)
        }
    }, contentColor = DecideTheme.colors.background) {

        AppHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .padding(it)
                .background(color = DecideTheme.colors.background)
        )
    }
}

@Composable
fun BottomBar(navController: NavController) {
    NavBottomBar(
        routeAssay = Assay.route,
        routeCategory = Category.route,
        routePassed = Passed.route,
        routeProfile = Profile.route,
        currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route,
        itemClickAssay = {
            navController.navigate(route = Assay.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        itemClickCategory = {
            navController.navigate(route = Category.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        itemClickPassed = {
            navController.navigate(route = Passed.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        itemClickProfile = {
            navController.navigate(route = Profile.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        })
}