package com.hieu10.vendoza.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieu10.vendoza.data.remote.models.Cart
import com.hieu10.vendoza.data.repository.CartRepository
import com.hieu10.vendoza.data.repository.ProductRepository
import com.hieu10.vendoza.viewmodel.state.ProductDetailsUIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val productId: String
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductDetailsUIState>(ProductDetailsUIState.Loading)
    val uiState: StateFlow<ProductDetailsUIState> = _uiState

    private val _addToCartResult = MutableSharedFlow<CartRepository.Result<Cart>>()
    val addToCartResult: SharedFlow<CartRepository.Result<Cart>> = _addToCartResult

    init {
        loadProduct()
    }

    fun loadProduct() {
        viewModelScope.launch {
            _uiState.value = ProductDetailsUIState.Loading
            when (val result = productRepository.getProductById(productId)) {
                is ProductRepository.Result.Success -> {
                    _uiState.value = ProductDetailsUIState.Success(result.data)
                }
                is ProductRepository.Result.Error -> {
                    _uiState.value = ProductDetailsUIState.Error(result.message)
                }
                else -> {}
            }
        }
    }

    fun addToCart(variantSku: String, quantity: Int) {
        viewModelScope.launch {
            val result = cartRepository.addItem(variantSku, quantity)
            _addToCartResult.emit(result)
        }
    }
}