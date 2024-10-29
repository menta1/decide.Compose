package com.decide.app.navigation.navHost

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.decide.app.feature.profile.editProfile.ui.EditProfileScreen
import com.decide.app.feature.profile.notification.NotificationScreen
import com.decide.app.feature.profile.settings.ui.SettingsScreen
import com.decide.app.navigation.EditProfile
import com.decide.app.navigation.Notification
import com.decide.app.navigation.Profile
import com.decide.app.navigation.ProfileRouteBranch
import com.decide.app.navigation.Settings

fun NavGraphBuilder.addNestedProfileGraph(
    navController: NavHostController,
) {
    navigation(startDestination = Settings.route, route = ProfileRouteBranch.route) {
        composable(route = EditProfile.route) {
            EditProfileScreen(
                onClickBack = {
                    navController.navigate(route = Profile.route) {
                        popUpTo(route = Profile.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Notification.route) {
            NotificationScreen()
        }
        composable(route = Settings.route) {
            SettingsScreen(
                onClickEditProfile = {
                    navController.navigate(route = EditProfile.route)
                },
                onClickLogOut = {
                    navController.navigate(route = Profile.route)
                },
                onClickNotifications = {
                    navController.navigate(route = Notification.route)
                }
            )
        }
    }
}