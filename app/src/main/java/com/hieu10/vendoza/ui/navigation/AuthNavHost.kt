package com.hieu10.vendoza.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hieu10.vendoza.ui.navigation.destinations.AuthDestinations
import com.hieu10.vendoza.ui.screens.auth.LoginScreen
import com.hieu10.vendoza.ui.screens.auth.RegisterScreen

@Composable
fun AuthNavHost(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AuthDestinations.Login
    ) {
        composable<AuthDestinations.Login> {
            LoginScreen(
                navController = navController,
                onLoginSuccess = onLoginSuccess
            )
        }
        composable<AuthDestinations.Register> {
            RegisterScreen(
                navController = navController,
                onBackToLogin = { navController.popBackStack() }
            )
        }
    }
}