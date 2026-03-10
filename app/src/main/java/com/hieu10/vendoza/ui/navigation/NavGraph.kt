package com.hieu10.vendoza.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hieu10.vendoza.ui.screens.auth.LoginScreen
import com.hieu10.vendoza.ui.screens.auth.RegisterScreen
import com.hieu10.vendoza.ui.screens.main.HomeScreen
import com.hieu10.vendoza.viewmodel.AuthViewModel
import com.hieu10.vendoza.viewmodel.factory.AuthVMFactory

@Composable
fun NavGraph(
    navController: NavHostController,
    authVMFactory: AuthVMFactory
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            val viewModel: AuthViewModel = viewModel(factory = authVMFactory)
            LoginScreen(
                viewModel = viewModel,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route) {
                        popUpTo(Screen.Login.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onNavigateToForgotPassword = {
                    // TODO: implement later
                },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Register.route) {
            val viewModel: AuthViewModel = viewModel(factory = authVMFactory)
            RegisterScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen()
        }
    }
}