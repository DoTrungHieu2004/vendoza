package com.hieu10.vendoza.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieu10.vendoza.data.repository.CategoryRepository
import com.hieu10.vendoza.data.repository.ProductRepository
import com.hieu10.vendoza.viewmodel.state.HomeUIState
import com.hieu10.vendoza.viewmodel.state.ProductListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    private val _productState = MutableStateFlow<ProductListState>(ProductListState.Loading)
    val productState: StateFlow<ProductListState> = _productState.asStateFlow()

    init {
        loadCategories()
        loadProducts()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = HomeUIState.Loading
            when (val result = categoryRepository.getCategories(tree = false)) {
                is CategoryRepository.Result.Success -> {
                    _uiState.value = HomeUIState.Success(result.data)
                }
                is CategoryRepository.Result.Error -> {
                    _uiState.value = HomeUIState.Error(result.message)
                }
                else -> {}
            }
        }
    }

    fun loadProducts() {
        viewModelScope.launch {
            _productState.value = ProductListState.Loading
            when (val result = productRepository.getProducts(page = 1, limit = 6)) {
                is ProductRepository.Result.Success -> {
                    _productState.value = ProductListState.Success(result.data.products)
                }
                is ProductRepository.Result.Error -> {
                    _productState.value = ProductListState.Error(result.message)
                }
                else -> {}
            }
        }
    }
}