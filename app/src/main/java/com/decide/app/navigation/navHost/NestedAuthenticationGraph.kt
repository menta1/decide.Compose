package com.decide.app.navigation.navHost

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.decide.app.account.ui.fillProfile.FillProfileScreen
import com.decide.app.account.ui.forgotPassword.ForgotPasswordScreen
import com.decide.app.account.ui.login.LoginScreen
import com.decide.app.account.ui.registration.RegistrationScreen
import com.decide.app.navigation.Assay
import com.decide.app.navigation.Authentication
import com.decide.app.navigation.AuthenticationRouteBranch
import com.decide.app.navigation.FillProfile
import com.decide.app.navigation.Profile
import com.decide.app.navigation.RecoveryAccount
import com.decide.app.navigation.Registration

fun NavGraphBuilder.addNestedAuthenticationGraph(
    navController: NavHostController,
) {

    navigation(
        route = AuthenticationRouteBranch.route, startDestination = Registration.route
    ) {

        composable(
            route = Registration.route
        ) {
            RegistrationScreen(onClickFillProfile = {
                navController.navigate(route = FillProfile.route)
            },
                onClickLogin = {},
                onClickBack = { navController.popBackStack() },
                onClickMainPage = {
                    navController.navigate(route = Assay.route) {
                        popUpTo(route = Assay.route) {
                            inclusive = true
                        }
                    }
                })
        }

        composable(
            route = FillProfile.route,
        ) {
            FillProfileScreen(onClickBack = {
                navController.navigate(route = Profile.route)
            }, onClickContinue = {
                navController.navigate(route = Profile.route)
            }, onClickMainPage = {
                navController.navigate(route = Assay.route) {
                    popUpTo(route = Assay.route) {
                        inclusive = true
                    }
                }
            })
        }


        composable(
            route = Authentication.route,
        ) {
            LoginScreen(onClickRegistration = { navController.navigate(Registration.route) },
                onAuth = { navController.navigate(Profile.route) },
                onClickMainPage = {
                    navController.navigate(route = Assay.route) {
                        popUpTo(route = Assay.route) {
                            inclusive = true
                        }
                    }
                },
                onClickForgotPassword = {
                    navController.navigate(route = RecoveryAccount.route) {
                        popUpTo(route = RecoveryAccount.route) {
                            inclusive = true
                        }
                    }
                })
        }

        composable(
            route = RecoveryAccount.route
        ) {
            ForgotPasswordScreen(onClickBack = {
                navController.navigate(route = Authentication.route) {
                    popUpTo(route = Authentication.route) {
                        inclusive = true
                    }
                }
            })
        }

    }
}