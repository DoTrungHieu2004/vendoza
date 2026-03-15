package com.hieu10.vendoza.ui.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hieu10.vendoza.R
import com.hieu10.vendoza.data.remote.ApiClient
import com.hieu10.vendoza.ui.components.ProductCard
import com.hieu10.vendoza.ui.components.bg.AppBG
import com.hieu10.vendoza.ui.theme.VendozaTheme
import com.hieu10.vendoza.viewmodel.ProductListViewModel
import com.hieu10.vendoza.viewmodel.factory.ProductListVMFactory
import com.hieu10.vendoza.viewmodel.state.ProductListUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    categoryId: String?,
    onNavigateBack: () -> Unit,
    onProductClick: (String) -> Unit,
    viewModel: ProductListViewModel = viewModel(
        factory = ProductListVMFactory(
            productRepository = ApiClient.productRepository,
            categoryRepository = ApiClient.categoryRepository,
            categoryId = categoryId
        )
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    val categoryName by viewModel.categoryName.collectAsState()

    AppBG {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = categoryName ?: stringResource(id = R.string.headline_products)) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.cd_back)
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            ProductListContent(
                modifier = Modifier.padding(paddingValues),
                uiState = uiState,
                onProductClick = onProductClick,
                loadProducts = { viewModel.loadProducts() }
            )
        }
    }
}

@Composable
private fun ProductListContent(
    modifier: Modifier = Modifier,
    uiState: ProductListUIState,
    onProductClick: (String) -> Unit,
    loadProducts: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (uiState) {
            is ProductListUIState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ProductListUIState.Success -> {
                val products = (uiState as ProductListUIState.Success).products

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(products) { product ->
                        ProductCard(
                            product = product,
                            onClick = { onProductClick(product.id) }
                        )
                    }
                }
            }
            is ProductListUIState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.error, uiState.message),
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = loadProducts) {
                        Text(text = stringResource(id = R.string.btn_retry))
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
        AppBG {
            ProductListContent(
                uiState = ProductListUIState.Loading,
                onProductClick = {},
                loadProducts = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenDark() {
    VendozaTheme(darkTheme = true) {
        AppBG {
            ProductListContent(
                uiState = ProductListUIState.Loading,
                onProductClick = {},
                loadProducts = {}
            )
        }
    }
}