package com.hieu10.vendoza.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")

    object ProductList : Screen("product_list/{categoryId}") {
        fun createRoute(categoryId: String) = "product_list/$categoryId"
    }
    object ProductDetails : Screen("product_details/{productId}") {
        fun createRoute(productId: String) = "product_details/$productId"
    }
}