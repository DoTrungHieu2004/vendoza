package com.hieu10.vendoza.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hieu10.vendoza.ui.components.BottomNavBar
import com.hieu10.vendoza.ui.navigation.BottomNavItem
import com.hieu10.vendoza.ui.screens.main.CartScreen
import com.hieu10.vendoza.ui.screens.main.HomeScreen
import com.hieu10.vendoza.ui.screens.main.ProfileScreen
import com.hieu10.vendoza.ui.screens.main.SearchScreen
import com.hieu10.vendoza.ui.theme.VendozaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<BottomNavItem.Home> { HomeScreen() }
            composable<BottomNavItem.Search> { SearchScreen() }
            composable<BottomNavItem.Cart> { CartScreen() }
            composable<BottomNavItem.Profile> { ProfileScreen() }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLight() {
    VendozaTheme(darkTheme = false) { MainScreen() }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenDark() {
    VendozaTheme(darkTheme = true) { MainScreen() }
}