package com.hieu10.vendoza.viewmodel.state

import com.hieu10.vendoza.data.remote.models.Product

sealed class ProductDetailsUIState {
    object Loading : ProductDetailsUIState()
    data class Success(val product: Product) : ProductDetailsUIState()
    data class Error(val message: String) : ProductDetailsUIState()
}