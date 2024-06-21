package com.decide.app.navigation.navHost

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.decide.app.feature.profile.editProfile.EditProfileScreen
import com.decide.app.feature.profile.notification.NotificationScreen
import com.decide.app.feature.profile.terms.TermsScreen
import com.decide.app.navigation.EditProfile
import com.decide.app.navigation.Notification
import com.decide.app.navigation.ProfileRouteBranch
import com.decide.app.navigation.Terms

fun NavGraphBuilder.addNestedProfileGraph(
    navController: NavHostController,
) {
    navigation(startDestination = EditProfile.route, route = ProfileRouteBranch.route) {
        composable(route = EditProfile.route) {
            EditProfileScreen()
        }
        composable(route = Notification.route) {
            NotificationScreen()
        }
        composable(route = Terms.route) {
            TermsScreen()
        }
    }
}