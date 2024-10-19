package com.decide.app.navigation

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.decide.app.activity.ShowAds
import com.decide.app.navigation.navHost.AppHost
import com.decide.uikit.theme.DecideTheme
import com.decide.uikit.ui.navigation.NavBottomBar


@Composable
fun AppNavScreen(
    startDestination: String,
    auth: String? = null,
    ads: ShowAds,
) {

    val itemsNavBottomBar = listOf(
        Assay.route, Category.route, Passed.route, Profile.route
    )
    val navController = rememberNavController()

    var isShowBottomBar by remember { mutableStateOf(false) }

    Scaffold(bottomBar = {
        if (itemsNavBottomBar.contains(
                navController.currentBackStackEntryAsState().value?.destination?.route
            )
        ) {
            BottomBar(
                navController, isShowBottomBar
            )
        }
    }, contentColor = DecideTheme.colors.background) {

        AppHost(
            navController = navController,
            startDestination = startDestination,
            ads = ads,
            modifier = Modifier
                .padding(it)
                .background(color = DecideTheme.colors.background)
        )
        LaunchedEffect(key1 = Unit) {
            isShowBottomBar = true
        }
    }
}

@Composable
fun BottomBar(
    navController: NavController,
    isVisible: Boolean
) {
    val context = LocalContext.current
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    }

    NavBottomBar(
        routeAssay = Assay.route,
        routeCategory = Category.route,
        routePassed = Passed.route,
        routeProfile = Profile.route,
        currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route,
        itemClickAssay = {
            navController.navigate(route = Assay.route) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            50,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    vibrator.vibrate(50)
                }
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        itemClickCategory = {
            navController.navigate(route = Category.route) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            50,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    vibrator.vibrate(50)
                }
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        itemClickPassed = {
            navController.navigate(route = Passed.route) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            50,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    vibrator.vibrate(50)
                }
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        itemClickProfile = {
            navController.navigate(route = Profile.route) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            50,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    vibrator.vibrate(50)
                }
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        isVisible = isVisible
    )
}