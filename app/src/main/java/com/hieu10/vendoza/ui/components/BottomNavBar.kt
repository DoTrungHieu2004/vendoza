package com.hieu10.vendoza.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hieu10.vendoza.ui.navigation.BottomNavItem
import com.hieu10.vendoza.ui.theme.VendozaTheme

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Cart,
        BottomNavItem.Profile
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            val selected = currentDestination?.hasRoute(route = item::class) == true

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (selected) item.iconFilled else item.icon,
                        contentDescription = stringResource(id = item.title)
                    )
                },
                label = {
                    Text(text = stringResource(id = item.title))
                },
                selected = selected,
                onClick = {
                    // Navigate to the selected item's route, popping up to start to avoid building a large back stack
                    navController.navigate(item) {
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

@Preview(showBackground = true)
@Composable
private fun PreviewBottomBarLight() {
    VendozaTheme(darkTheme = false) {
        BottomNavBar(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBottomBarDark() {
    VendozaTheme(darkTheme = true) {
        BottomNavBar(navController = rememberNavController())
    }
}