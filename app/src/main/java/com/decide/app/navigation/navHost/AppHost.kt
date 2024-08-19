package com.decide.app.navigation.navHost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.decide.app.feature.assay.mainAssay.ui.AssayMainScreen
import com.decide.app.feature.category.mainCategory.ui.CategoryScreen
import com.decide.app.feature.category.specificCategory.ui.CategoriesSpecificScreen
import com.decide.app.feature.passed.ui.PassedScreen
import com.decide.app.feature.profile.profileMain.ProfileScreen
import com.decide.app.navigation.Assay
import com.decide.app.navigation.AssayRouteBranch
import com.decide.app.navigation.AssayWithResult
import com.decide.app.navigation.CategoriesSpecific
import com.decide.app.navigation.Category
import com.decide.app.navigation.Passed
import com.decide.app.navigation.Profile

@Composable
fun AppHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier
    //authDestination: String?,
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = Assay.route) {
            AssayMainScreen(
                modifier = modifier,
                onClickAssay = { argument ->
                    navController.navigate(route = AssayRouteBranch.route + "$argument")
                }
            )
        }
        composable(route = Category.route) {
            CategoryScreen(
                modifier = modifier,
                onClickSpecificCategory = { argument ->
                    navController.navigate(route = CategoriesSpecific.route + argument)
                }
            )
        }

        composable(
            route = CategoriesSpecific.route + "{idCategory}",
            arguments = listOf(navArgument("idCategory") {
                type = NavType.IntType
            })
        ) {
            CategoriesSpecificScreen(
                onClickAssay = { argument ->
                    navController.navigate(route = AssayRouteBranch.route + argument)

                },
                onClickBack = {})
        }

        composable(route = Passed.route) {
            PassedScreen(
                onClickResult = { id: Int, date: Long ->
                    navController.navigate(route = "${AssayWithResult.route}?idAssay=$id&dateAssay=$date")
                }
            )
        }
        composable(route = Profile.route) {
            ProfileScreen()
        }
        addNestedAssayGraph(navController = navController)
        addNestedProfileGraph(navController = navController)
    }
}