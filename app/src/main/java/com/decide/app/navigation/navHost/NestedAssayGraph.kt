package com.decide.app.navigation.navHost

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.decide.app.feature.assay.assayCheckStarted.AssayCheckStartedScreen
import com.decide.app.feature.assay.assayDescription.ui.AssayDescriptionScreen
import com.decide.app.feature.assay.assayProcess.ui.AssayProcessScreen
import com.decide.app.feature.assay.assayResult.ui.AssayWithResultScreen
import com.decide.app.navigation.Assay
import com.decide.app.navigation.AssayCheckStarted
import com.decide.app.navigation.AssayDescription
import com.decide.app.navigation.AssayProcess
import com.decide.app.navigation.AssayRouteBranch
import com.decide.app.navigation.AssayWithResult

fun NavGraphBuilder.addNestedAssayGraph(
    navController: NavHostController
) {
    navigation(
        route = AssayRouteBranch.route + "{idAssay}",
        startDestination = AssayDescription.route,
        arguments = listOf(navArgument("idAssay") { type = NavType.IntType })
    ) {

        composable(
            route = AssayDescription.route,
        ) { entry ->
            val parentEntry = remember(entry) {
                navController.getBackStackEntry(AssayRouteBranch.route + "{idAssay}")
            }
            val idAssay = parentEntry.arguments?.getInt("idAssay")
            AssayDescriptionScreen(onClickBack = {
                navController.popBackStack()
            }, onClickStart = { argument ->
                navController.navigate(AssayProcess.route + "$argument")
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


        composable(
            route = AssayProcess.route + "{idAssay}", arguments = listOf(navArgument("idAssay") {
                type = NavType.IntType
            })
        ) { entry ->
            val idAssay = entry.arguments?.getInt("idAssay")
            AssayProcessScreen(
                onClickBack = {
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
            arguments = listOf(
                navArgument("idAssay") {
                    type = NavType.IntType
                }, navArgument("dateAssay") {
                    type = NavType.StringType
                    defaultValue = "-1"
                    nullable = true
                })
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