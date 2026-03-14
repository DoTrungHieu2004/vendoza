package com.hieu10.vendoza.viewmodel.state

import com.hieu10.vendoza.data.remote.models.Product

sealed class ProductListState {
    object Loading : ProductListState()
    data class Success(val products: List<Product>) : ProductListState()
    data class Error(val message: String) : ProductListState()
}