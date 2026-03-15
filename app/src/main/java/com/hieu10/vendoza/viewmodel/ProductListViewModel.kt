package com.hieu10.vendoza.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieu10.vendoza.data.repository.CategoryRepository
import com.hieu10.vendoza.data.repository.ProductRepository
import com.hieu10.vendoza.viewmodel.state.ProductListUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val categoryId: String?
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductListUIState>(ProductListUIState.Loading)
    val uiState: StateFlow<ProductListUIState> = _uiState

    private val _categoryName = MutableStateFlow<String?>(null)
    val categoryName: StateFlow<String?> = _categoryName

    init {
        if (categoryId != null) {
            loadCategoryName()
        }
        loadProducts()
    }

    private fun loadCategoryName() {
        viewModelScope.launch {
            val result = categoryRepository.getCategoryByIdOrSlug(categoryId)
            if (result is CategoryRepository.Result.Success) {
                _categoryName.value = result.data.name
            }
        }
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductListUIState.Loading
            val result = productRepository.getProducts(
                page = 1,
                limit = 20,
                categoryId = categoryId
            )
            when (result) {
                is ProductRepository.Result.Success -> {
                    _uiState.value = ProductListUIState.Success(result.data.products)
                }
                is ProductRepository.Result.Error -> {
                    _uiState.value = ProductListUIState.Error(result.message)
                }
                else -> {}
            }
        }
    }
}