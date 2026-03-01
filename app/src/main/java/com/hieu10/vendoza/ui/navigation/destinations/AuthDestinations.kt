package com.hieu10.vendoza.ui.navigation.destinations

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthDestinations {
    @Serializable
    data object Login : AuthDestinations()

    @Serializable
    data object Register : AuthDestinations()
}