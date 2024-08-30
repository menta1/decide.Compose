package com.decide.app.navigation.navHost

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.decide.app.feature.assay.assayCheckStarted.AssayCheckStartedScreen
import com.decide.app.feature.assay.assayDescription.ui.AssayDescriptionScreen
import com.decide.app.feature.assay.assayProcess.ui.assayText.AssayTextScreen
import com.decide.app.feature.assay.assayProcess.ui.assayTimer.AssayTimerScreen
import com.decide.app.feature.assay.assayResult.ui.AssayWithResultScreen
import com.decide.app.navigation.Assay
import com.decide.app.navigation.AssayCheckStarted
import com.decide.app.navigation.AssayDescription
import com.decide.app.navigation.AssayRouteBranch
import com.decide.app.navigation.AssayText
import com.decide.app.navigation.AssayTimer
import com.decide.app.navigation.AssayWithResult

fun NavGraphBuilder.addNestedAssayGraph(
    navController: NavHostController
) {
    navigation(
        route = AssayRouteBranch.route + "{idAssay}",
        startDestination = AssayDescription.route,
        arguments = listOf(navArgument("idAssay") { type = NavType.IntType })
    ) {

        composable(route = AssayDescription.route, enterTransition = {
            fadeIn(
                animationSpec = tween(
                    100, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Up
            )
        }, exitTransition = {
            fadeOut(
                animationSpec = tween(
                    100, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(200, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.Down
            )
        }) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(AssayRouteBranch.route + "{idAssay}")
            }
            val idAssay = parentEntry.arguments?.getInt("idAssay")
            AssayDescriptionScreen(onClickBack = {
                navController.popBackStack()
            }, onStartAssayText = { argument ->
                navController.navigate(AssayText.route + "$argument")
            }, onStartAssayTimer = { argument ->
                navController.navigate(AssayTimer.route + "$argument")
            }, idAssay = idAssay
            )
        }
        /**
         * AssayCheckStartedScreen() должен спрашивать продолжить ли тест который начали ранее
         * пока пропущено!
         */
        composable(route = AssayCheckStarted.route) {
            AssayCheckStartedScreen()
        }


        composable(route = AssayText.route + "{idAssay}", arguments = listOf(
            navArgument("idAssay") {
                type = NavType.IntType
            },
        ), enterTransition = {
            fadeIn(
                animationSpec = tween(
                    100, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Up
            )
        }, exitTransition = {
            fadeOut(
                animationSpec = tween(
                    100, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(200, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.Down
            )
        }) {
            AssayTextScreen(onClickBack = {
                navController.navigate(Assay.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }, onClickDone = { id ->
                navController.navigate("${AssayWithResult.route}?idAssay=$id")
            })
        }

        composable(route = AssayTimer.route + "{idAssay}", arguments = listOf(
            navArgument("idAssay") {
                type = NavType.IntType
            },
        ), enterTransition = {
            fadeIn(
                animationSpec = tween(
                    100, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Up
            )
        }, exitTransition = {
            fadeOut(
                animationSpec = tween(
                    100, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(200, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.Down
            )
        }) {
            AssayTimerScreen(onClickBack = {
                navController.navigate(Assay.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }, onClickDone = { id ->
                navController.navigate("${AssayWithResult.route}?idAssay=$id")
            })
        }

        composable(
            route = "${AssayWithResult.route}?idAssay={idAssay}&dateAssay={dateAssay}",
            arguments = listOf(navArgument("idAssay") {
                type = NavType.IntType
            }, navArgument("dateAssay") {
                type = NavType.StringType
                defaultValue = "-1"
                nullable = true
            }),
//            enterTransition = {
//                fadeIn(
//                    animationSpec = tween(
//                        100, easing = LinearEasing
//                    )
//                ) + slideIntoContainer(
//                    animationSpec = tween(200, easing = EaseIn),
//                    towards = AnimatedContentTransitionScope.SlideDirection.Up
//                )
//            }, exitTransition = {
//                fadeOut(
//                    animationSpec = tween(
//                        100, easing = LinearEasing
//                    )
//                ) + slideOutOfContainer(
//                    animationSpec = tween(200, easing = EaseOut),
//                    towards = AnimatedContentTransitionScope.SlideDirection.Down
//                )
//            }
        ) {
            AssayWithResultScreen(onClickExit = {
                navController.navigate(Assay.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            })
        }
    }
}