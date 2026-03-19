package com.hieu10.vendoza.viewmodel.state

import com.hieu10.vendoza.data.remote.models.Product

sealed class SearchUIState {
    object Idle : SearchUIState()
    object Loading : SearchUIState()
    data class Success(val products: List<Product>) : SearchUIState()
    data class Error(val message: String) : SearchUIState()
}