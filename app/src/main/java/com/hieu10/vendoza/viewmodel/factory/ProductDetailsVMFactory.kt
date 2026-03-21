package com.hieu10.vendoza.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hieu10.vendoza.data.repository.CartRepository
import com.hieu10.vendoza.data.repository.ProductRepository
import com.hieu10.vendoza.viewmodel.ProductDetailsViewModel
import com.hieu10.vendoza.viewmodel.ProductListViewModel

class ProductDetailsVMFactory(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val productId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailsViewModel(productRepository, cartRepository, productId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}