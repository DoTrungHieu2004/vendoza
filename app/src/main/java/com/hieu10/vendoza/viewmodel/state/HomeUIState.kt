package com.hieu10.vendoza.viewmodel.state

import com.hieu10.vendoza.data.remote.models.Category

sealed class HomeUIState {
    object Loading : HomeUIState()
    data class Success(val categories: List<Category>) : HomeUIState()
    data class Error(val message: String) : HomeUIState()
}