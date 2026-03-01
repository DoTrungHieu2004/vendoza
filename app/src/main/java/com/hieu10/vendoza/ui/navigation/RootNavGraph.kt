package com.hieu10.vendoza.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hieu10.vendoza.ui.navigation.destinations.AuthDestinations
import com.hieu10.vendoza.ui.screens.MainScreen
import kotlinx.serialization.Serializable

@Composable
fun RootNavGraph() {
    // Simulate auth state
    var isLoggedIn by remember { mutableStateOf(false) }

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) MainGraph else AuthGraph
    ) {
        // Auth graph
        composable<AuthGraph> {
            AuthNavHost(
                onLoginSuccess = { isLoggedIn = true },
                onNavigateToRegister = { navController.navigate(AuthDestinations.Register) }
            )
        }

        // Main graph (app with bottom nav)
        composable<MainGraph> {
            MainScreen(
                onLogout = { isLoggedIn = false }
            )
        }
    }
}

// Dummy destinations for the root graph
@Serializable
data object AuthGraph

@Serializable
data object MainGraph