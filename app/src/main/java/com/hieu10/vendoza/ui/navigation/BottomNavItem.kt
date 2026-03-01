package com.hieu10.vendoza.ui.navigation

import androidx.annotation.StringRes
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
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
sealed class BottomNavItem(
    val route: String,
    @field:StringRes val title: Int,
    @Contextual val icon: ImageVector,
    @Contextual val iconFilled: ImageVector
) {
    @Serializable
    data object Home : BottomNavItem(
        route = "home",
        title = R.string.nav_home,
        icon = Icons.Outlined.Home,
        iconFilled = Icons.Default.Home
    )

    @Serializable
    data object Search : BottomNavItem(
        route = "search",
        title = R.string.nav_search,
        icon = Icons.Outlined.Search,
        iconFilled = Icons.Default.Search
    )

    @Serializable
    data object Cart : BottomNavItem(
        route = "cart",
        title = R.string.nav_cart,
        icon = Icons.Outlined.ShoppingCart,
        iconFilled = Icons.Default.ShoppingCart
    )

    @Serializable
    data object Profile : BottomNavItem(
        route = "profile",
        title = R.string.nav_profile,
        icon = Icons.Outlined.Person,
        iconFilled = Icons.Default.Person
    )
}