package com.hieu10.vendoza.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.hieu10.vendoza.R

sealed class BottomNavItem(
    val route: String,
    val title: Int,
    val icon: ImageVector,
    val iconSelected: ImageVector? = null
) {
    object Home : BottomNavItem(
        route = "home",
        title = R.string.nav_home,
        icon = Icons.Outlined.Home,
        iconSelected = Icons.Filled.Home
    )
    object Search : BottomNavItem(
        route = "search",
        title = R.string.nav_search,
        icon = Icons.Outlined.Search,
        iconSelected = Icons.Filled.Search
    )
    object Cart : BottomNavItem(
        route = "cart",
        title = R.string.nav_cart,
        icon = Icons.Outlined.ShoppingCart,
        iconSelected = Icons.Filled.ShoppingCart
    )
    object Profile : BottomNavItem(
        route = "profile",
        title = R.string.nav_profile,
        icon = Icons.Outlined.Person,
        iconSelected = Icons.Filled.Person
    )
}