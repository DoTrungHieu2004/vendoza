package com.hieu10.vendoza.viewmodel.state

import com.hieu10.vendoza.data.remote.models.Product

sealed class ProductListUIState {
    object Loading : ProductListUIState()
    data class Success(val products: List<Product>) : ProductListUIState()
    data class Error(val message: String) : ProductListUIState()
}