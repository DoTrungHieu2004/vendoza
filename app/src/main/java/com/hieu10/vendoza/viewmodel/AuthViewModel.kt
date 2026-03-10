package com.hieu10.vendoza.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieu10.vendoza.data.remote.models.request.LoginRequest
import com.hieu10.vendoza.data.remote.models.request.RegisterRequest
import com.hieu10.vendoza.data.repository.AuthRepository
import com.hieu10.vendoza.viewmodel.state.AuthUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<AuthUIState>(AuthUIState.Idle)
    val uiState: StateFlow<AuthUIState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUIState.Loading
            when (val result = authRepository.login(LoginRequest(email, password))) {
                is AuthRepository.Result.Success -> _uiState.value = AuthUIState.Success(result.data)
                is AuthRepository.Result.Error -> _uiState.value = AuthUIState.Error(result.message)
                else -> {}  // Loading not used here
            }
        }
    }

    fun register(username: String, email: String, phone: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUIState.Loading
            when (val result = authRepository.register(RegisterRequest(username, email, phone, password))) {
                is AuthRepository.Result.Success -> _uiState.value = AuthUIState.Success(result.data)
                is AuthRepository.Result.Error -> _uiState.value = AuthUIState.Error(result.message)
                else -> {}
            }
        }
    }

    fun resetState() {
        _uiState.value = AuthUIState.Idle
    }
}