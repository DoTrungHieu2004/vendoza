package com.hieu10.vendoza.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hieu10.vendoza.data.remote.ApiClient
import com.hieu10.vendoza.ui.navigation.BottomNavItem
import com.hieu10.vendoza.ui.screens.main.CartScreen
import com.hieu10.vendoza.ui.screens.main.HomeScreen
import com.hieu10.vendoza.ui.screens.main.ProfileScreen
import com.hieu10.vendoza.ui.screens.main.SearchScreen
import com.hieu10.vendoza.ui.theme.VendozaTheme
import com.hieu10.vendoza.viewmodel.HomeViewModel
import com.hieu10.vendoza.viewmodel.factory.HomeVMFactory

@Composable
fun MainScreen(onLogout: () -> Unit) {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Cart,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { item ->
                    val selected = currentRoute == item.route
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if (selected && item.iconSelected != null) {
                                    item.iconSelected
                                } else {
                                    item.icon
                                },
                                contentDescription = stringResource(id = item.title)
                            )
                        },
                        label = { Text(text = stringResource(id = item.title)) },
                        selected = selected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomNavItem.Home.route) {
                val homeViewModel: HomeViewModel = viewModel(
                    factory = HomeVMFactory(
                        ApiClient.categoryRepository,
                        ApiClient.productRepository
                    )
                )

                HomeScreen(
                    viewModel = homeViewModel,
                    onCategoryClick = {},
                    onProductClick = {}
                )
            }
            composable(BottomNavItem.Search.route) { SearchScreen() }
            composable(BottomNavItem.Cart.route) { CartScreen() }
            composable(BottomNavItem.Profile.route) { ProfileScreen() }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLight() {
    VendozaTheme(darkTheme = false) {
        MainScreen(onLogout = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenDark() {
    VendozaTheme(darkTheme = true) {
        MainScreen(onLogout = {})
    }
}