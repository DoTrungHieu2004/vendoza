package com.hieu10.vendoza.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hieu10.vendoza.data.repository.CategoryRepository
import com.hieu10.vendoza.data.repository.ProductRepository
import com.hieu10.vendoza.viewmodel.ProductListViewModel

class ProductListVMFactory(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val categoryId: String?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductListViewModel(productRepository, categoryRepository, categoryId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}