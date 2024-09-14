package com.decide.app.navigation.navHost

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.decide.app.feature.assay.assayMain.ui.AssayMainScreen
import com.decide.app.feature.category.mainCategory.ui.CategoryScreen
import com.decide.app.feature.category.specificCategory.ui.CategoriesSpecificScreen
import com.decide.app.feature.passed.ui.PassedScreen
import com.decide.app.feature.profile.profileMain.ui.ProfileScreen
import com.decide.app.navigation.Assay
import com.decide.app.navigation.AssayRouteBranch
import com.decide.app.navigation.AssayWithResult
import com.decide.app.navigation.Authentication
import com.decide.app.navigation.CategoriesSpecific
import com.decide.app.navigation.Category
import com.decide.app.navigation.Passed
import com.decide.app.navigation.Profile
import com.decide.app.navigation.Registration
import com.decide.app.navigation.Settings

@Composable
fun AppHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier,
) {
    var previousBackStackEntry = remember {
        Assay.route
    }

    NavHost(
        navController = navController, startDestination = startDestination, modifier = modifier
    ) {
        composable(route = Assay.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )

            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            }) {
            previousBackStackEntry = Assay.route
            AssayMainScreen(onClickAssay = { argument ->
                previousBackStackEntry = AssayRouteBranch.route
                navController.navigate(route = AssayRouteBranch.route + "$argument")
            })
        }

        composable(route = Category.route, enterTransition = {
            when (previousBackStackEntry) {
                "ASSAY_LIST" -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                }

                else -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                }
            }

        }, exitTransition = {
            when (navController.currentBackStackEntry?.destination?.route) {
                "ASSAY_LIST" -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                }

                else -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                }
            }

        }) {
            previousBackStackEntry = Category.route
            CategoryScreen(onClickSpecificCategory = { argument ->
                navController.navigate(route = CategoriesSpecific.route + argument)
            })
        }

        composable(
            route = CategoriesSpecific.route + "{idCategory}",
            arguments = listOf(navArgument("idCategory") {
                type = NavType.IntType
            })
        ) {
            CategoriesSpecificScreen(onClickAssay = { argument ->
                navController.navigate(route = AssayRouteBranch.route + argument)
            }, onClickBack = {
                navController.popBackStack()
            })
        }

        composable(route = Passed.route, enterTransition = {
            when (previousBackStackEntry) {
                "PROFILE" -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                }

                else -> {
                    slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                }
            }
        }, exitTransition = {
            when (navController.currentBackStackEntry?.destination?.route) {
                "PROFILE" -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(500)
                    )
                }

                else -> {
                    slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(500)
                    )
                }
            }
        }) {
            previousBackStackEntry = Passed.route
            PassedScreen(onClickResult = { id: Int, date: Long ->
                navController.navigate(route = "${AssayWithResult.route}?idAssay=$id&dateAssay=$date")
            })
        }
        composable(route = Profile.route, enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(500)
            )

        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(500)
            )
        }) {
            previousBackStackEntry = Profile.route
            ProfileScreen(
                onClickSetting = {
                    navController.navigate(route = Settings.route)
                },
                onClickLogin = {
                    navController.navigate(route = Authentication.route)
                },
                onClickRegistration = {
                    navController.navigate(route = Registration.route)
                },
            )
        }
        addNestedAssayGraph(navController = navController)
        addNestedProfileGraph(navController = navController)
        addNestedAuthenticationGraph(navController = navController)
    }
}