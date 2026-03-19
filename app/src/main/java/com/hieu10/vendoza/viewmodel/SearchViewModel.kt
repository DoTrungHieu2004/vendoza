package com.hieu10.vendoza.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hieu10.vendoza.data.local.SearchHistoryManager
import com.hieu10.vendoza.data.repository.ProductRepository
import com.hieu10.vendoza.viewmodel.state.SearchUIState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val productRepository: ProductRepository,
    private val historyManager: SearchHistoryManager
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _uiState = MutableStateFlow<SearchUIState>(SearchUIState.Idle)
    val uiState: StateFlow<SearchUIState> = _uiState

    private val _history = MutableStateFlow<List<String>>(emptyList())
    val history: StateFlow<List<String>> = _history

    init {
        viewModelScope.launch {
            historyManager.history.collect { _history.value = it }
        }

        // Debounce search to avoid too many requests
        viewModelScope.launch {
            _searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isBlank()) {
                        _uiState.value = SearchUIState.Idle
                    } else {
                        search(query)
                    }
                }
        }
    }

    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    private fun search(query: String) {
        viewModelScope.launch {
            _uiState.value = SearchUIState.Loading
            when (val result = productRepository.getProducts(query = query, limit = 20)) {
                is ProductRepository.Result.Success -> {
                    _uiState.value = SearchUIState.Success(result.data.products)
                }
                is ProductRepository.Result.Error -> {
                    _uiState.value = SearchUIState.Error(result.message)
                }
                else -> {}
            }
        }
    }

    fun clearResults() {
        _uiState.value = SearchUIState.Idle
        _searchQuery.value = ""
    }

    fun clearHistory() {
        viewModelScope.launch {
            historyManager.clearHistory()
        }
    }
}