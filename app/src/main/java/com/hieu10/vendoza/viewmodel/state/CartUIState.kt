package com.hieu10.vendoza.viewmodel.state

import com.hieu10.vendoza.data.remote.models.Cart

sealed class CartUIState {
    object Loading : CartUIState()
    data class Success(val cart: Cart) : CartUIState()
    data class Error(val message: String) : CartUIState()
}