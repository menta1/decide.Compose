package com.decide.app.navigation.navHost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.decide.app.feature.assay.mainAssay.ui.AssayMainScreen
import com.decide.app.feature.category.CategoryScreen
import com.decide.app.feature.passed.PassedScreen
import com.decide.app.feature.profile.profileMain.ProfileScreen
import com.decide.app.navigation.Assay
import com.decide.app.navigation.AssayRouteBranch
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
            CategoryScreen()
        }
        composable(route = Passed.route) {
            PassedScreen()
        }
        composable(route = Profile.route) {
            ProfileScreen()
        }
        addNestedAssayGraph(navController = navController)
        addNestedProfileGraph(navController = navController)
    }
}