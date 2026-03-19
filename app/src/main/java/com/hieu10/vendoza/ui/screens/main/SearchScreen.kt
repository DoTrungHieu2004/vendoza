package com.hieu10.vendoza.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hieu10.vendoza.R
import com.hieu10.vendoza.data.local.SearchHistoryManager
import com.hieu10.vendoza.data.remote.ApiClient
import com.hieu10.vendoza.ui.components.ProductCard
import com.hieu10.vendoza.ui.components.SearchHistoryItem
import com.hieu10.vendoza.ui.theme.VendozaTheme
import com.hieu10.vendoza.viewmodel.SearchViewModel
import com.hieu10.vendoza.viewmodel.factory.SearchVMFactory
import com.hieu10.vendoza.viewmodel.state.SearchUIState

@Composable
fun SearchScreen(
    onProductClick: (String) -> Unit,
    historyManager: SearchHistoryManager,
    modifier: Modifier = Modifier
) {
    val viewModel: SearchViewModel = viewModel(
        factory = SearchVMFactory(ApiClient.productRepository, historyManager)
    )
    val uiState by viewModel.uiState.collectAsState()
    val query by viewModel.searchQuery.collectAsState()
    val history by viewModel.history.collectAsState()

    SearchContent(
        query = query,
        onQueryChange = { viewModel.updateQuery(it) },
        onClearClick = { viewModel.clearResults() },
        uiState = uiState,
        onProductClick = onProductClick,
        history = history,
        onHistoryItemClick = { historyQuery ->
            viewModel.updateQuery(historyQuery)
        },
        onClearHistory = { viewModel.clearHistory() },
        modifier = modifier
    )
}

@Composable
private fun SearchContent(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    uiState: SearchUIState,
    history: List<String>,
    onHistoryItemClick: (String) -> Unit,
    onClearHistory: () -> Unit,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        // Search bar
        Surface(
            tonalElevation = 2.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = query,
                    onValueChange = onQueryChange,
                    placeholder = { Text(text = stringResource(id = R.string.label_search)) },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    ),
                    singleLine = true
                )
                if (query.isNotBlank()) {
                    IconButton(onClick = onClearClick) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.cd_clear)
                        )
                    }
                }
            }
        }

        // Results area
        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            when (uiState) {
                is SearchUIState.Idle -> {
                    if (history.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.start_search),
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(id = R.string.section_recent_searches),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                TextButton(onClick = onClearHistory) {
                                    Text(
                                        text = stringResource(id = R.string.txt_btn_clear),
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                            LazyColumn {
                                items(history) { item ->
                                    SearchHistoryItem(
                                        query = item,
                                        onClick = onHistoryItemClick
                                    )
                                }
                            }
                        }
                    }
                }
                is SearchUIState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is SearchUIState.Success -> {
                    val products = uiState.products
                    if (products.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.no_products_found),
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(products) { product ->
                                ProductCard(
                                    product = product,
                                    onClick = { onProductClick(product.id) }
                                )
                            }
                        }
                    }
                }
                is SearchUIState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.error, uiState.message),
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { onQueryChange(query) }) {
                            Text(text = stringResource(id = R.string.btn_retry))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLight() {
    VendozaTheme(darkTheme = false) {
        SearchContent(
            query = "",
            onQueryChange = {},
            onClearClick = {},
            uiState = SearchUIState.Idle,
            onProductClick = {},
            history = listOf("history 1", "history 2"),
            onHistoryItemClick = {},
            onClearHistory = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenDark() {
    VendozaTheme(darkTheme = true) {
        SearchContent(
            query = "",
            onQueryChange = {},
            onClearClick = {},
            uiState = SearchUIState.Idle,
            onProductClick = {},
            history = listOf("history 1", "history 2"),
            onHistoryItemClick = {},
            onClearHistory = {}
        )
    }
}