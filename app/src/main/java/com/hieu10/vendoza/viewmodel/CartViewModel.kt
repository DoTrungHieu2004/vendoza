package com.hieu10.vendoza.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieu10.vendoza.data.repository.CartRepository
import com.hieu10.vendoza.viewmodel.state.CartUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<CartUIState>(CartUIState.Loading)
    val uiState: StateFlow<CartUIState> = _uiState.asStateFlow()

    init {
        loadCart()
    }

    fun loadCart() {
        viewModelScope.launch {
            _uiState.value = CartUIState.Loading
            when (val result = cartRepository.getCart()) {
                is CartRepository.Result.Success -> {
                    _uiState.value = CartUIState.Success(result.data)
                }
                is CartRepository.Result.Error -> {
                    _uiState.value = CartUIState.Error(result.message)
                }
                else -> {}
            }
        }
    }

    fun addItem(variantSku: String, quantity: Int) {
        viewModelScope.launch {
            when (val result = cartRepository.addItem(variantSku, quantity)) {
                is CartRepository.Result.Success -> {
                    _uiState.value = CartUIState.Success(result.data)
                }
                is CartRepository.Result.Error -> {
                    loadCart()
                }
                else -> {}
            }
        }
    }

    fun updateItem(variantSku: String, quantity: Int) {
        viewModelScope.launch {
            when (val result = cartRepository.updateItem(variantSku, quantity)) {
                is CartRepository.Result.Success -> {
                    _uiState.value = CartUIState.Success(result.data)
                }
                is CartRepository.Result.Error -> {
                    loadCart()
                }
                else -> {}
            }
        }
    }

    fun removeItem(variantSku: String) {
        viewModelScope.launch {
            when (val result = cartRepository.removeItem(variantSku)) {
                is CartRepository.Result.Success -> {
                    _uiState.value = CartUIState.Success(result.data)
                }
                is CartRepository.Result.Error -> {
                    loadCart()
                }
                else -> {}
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            when (val result = cartRepository.clearCart()) {
                is CartRepository.Result.Success -> {
                    _uiState.value = CartUIState.Success(result.data)
                }
                is CartRepository.Result.Error -> {
                    loadCart()
                }
                else -> {}
            }
        }
    }
}