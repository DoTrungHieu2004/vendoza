package com.hieu10.vendoza.viewmodel.state

import com.hieu10.vendoza.data.remote.models.User

sealed class AuthUIState {
    object Idle : AuthUIState()
    object Loading : AuthUIState()
    data class Success(val user: User) : AuthUIState()
    data class Error(val message: String) : AuthUIState()
}