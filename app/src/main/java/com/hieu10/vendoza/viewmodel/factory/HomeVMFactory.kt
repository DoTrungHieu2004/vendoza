package com.hieu10.vendoza.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hieu10.vendoza.data.repository.CategoryRepository
import com.hieu10.vendoza.data.repository.ProductRepository
import com.hieu10.vendoza.viewmodel.HomeViewModel

class HomeVMFactory(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(categoryRepository, productRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}