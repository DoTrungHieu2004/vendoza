package com.hieu10.vendoza.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.vendoza.R
import com.hieu10.vendoza.ui.components.bg.AppBG
import com.hieu10.vendoza.ui.components.home.CategoryChipItem
import com.hieu10.vendoza.ui.components.home.CategoryChips
import com.hieu10.vendoza.ui.components.home.HeroBanner
import com.hieu10.vendoza.ui.components.home.ProductGrid
import com.hieu10.vendoza.ui.theme.VendozaTheme
import com.hieu10.vendoza.viewmodel.HomeViewModel
import com.hieu10.vendoza.viewmodel.state.HomeUIState
import com.hieu10.vendoza.viewmodel.state.ProductListState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onCategoryClick: (String) -> Unit,
    onProductClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val productState by viewModel.productState.collectAsState()
    
    AppBG {
        HomeContent(
            uiState = uiState,
            productState = productState,
            onCategoryClick = onCategoryClick,
            onProductClick = onProductClick
        )
    }
}

@Composable
private fun HomeContent(
    uiState: HomeUIState,
    productState: ProductListState,
    onCategoryClick: (String) -> Unit,
    onProductClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeroBanner(modifier = Modifier.padding(8.dp))
        }

        // Category section
        item {
            Text(
                text = stringResource(id = R.string.headline_shop_by_category),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item {
            when (uiState) {
                is HomeUIState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is HomeUIState.Success -> {
                    val categories = (uiState as HomeUIState.Success).categories
                    CategoryChips(
                        categories = categories.map { category ->
                            CategoryChipItem(category.id, category.name)
                        },
                        onCategoryClick = onCategoryClick
                    )
                }
                is HomeUIState.Error -> {}
            }
        }

        // Product grid section
        item {
            Text(
                text = stringResource(id = R.string.headline_featured_products),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item {
            when (productState) {
                is ProductListState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is ProductListState.Success -> {
                    val products = (productState as ProductListState.Success).products

                    ProductGrid(
                        products = products,
                        onProductClick = onProductClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                is ProductListState.Error -> {}
            }
        }

        // Spacer at end
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLight() {
    VendozaTheme(darkTheme = false) {
        AppBG {
            HomeContent(
                uiState = HomeUIState.Loading,
                productState = ProductListState.Loading,
                onCategoryClick = {},
                onProductClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenDark() {
    VendozaTheme(darkTheme = true) {
        AppBG {
            HomeContent(
                uiState = HomeUIState.Loading,
                productState = ProductListState.Loading,
                onCategoryClick = {},
                onProductClick = {}
            )
        }
    }
}