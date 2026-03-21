package com.hieu10.vendoza.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.hieu10.vendoza.ui.components.CartItem
import com.hieu10.vendoza.ui.components.CartItemUI
import com.hieu10.vendoza.data.sample.sampleItems
import com.hieu10.vendoza.ui.theme.VendozaTheme
import com.hieu10.vendoza.viewmodel.CartViewModel
import com.hieu10.vendoza.viewmodel.factory.CartVMFactory
import com.hieu10.vendoza.viewmodel.state.CartUIState

@Composable
fun CartScreen(
    onCheckoutClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val viewModel: CartViewModel = viewModel(
        factory = CartVMFactory(ApiClient.cartRepository)
    )
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is CartUIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is CartUIState.Success -> {
            val cart = (uiState as CartUIState.Success).cart
            val items = cart.items.map { item ->
                CartItemUI(
                    id = item.productId,
                    variantSku = item.variantSku,
                    imageUrl = item.productImage,
                    name = item.productName ?: "Product",
                    attributes = item.variantAttributes?.entries?.joinToString(", ") { "${it.key}: ${it.value}" } ?: "",
                    price = item.priceSnapshot,
                    quantity = item.quantity,
                    stock = item.currentStock ?: 0,
                    unavailable = item.unavailable ?: false
                )
            }
            CartContent(
                items = items,
                subtotal = cart.subtotal,
                onQuantityChange = { variantSku, newQuantity ->
                    viewModel.updateItem(variantSku, newQuantity)
                },
                onRemove = { variantSku ->
                    viewModel.removeItem(variantSku)
                },
                onCheckoutClick = onCheckoutClick,
                modifier = modifier
            )
        }
        is CartUIState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.error, uiState as CartUIState.Error))
            }
        }
    }
}

@Composable
private fun CartContent(
    items: List<CartItemUI>,
    subtotal: Double,
    onQuantityChange: (String, Int) -> Unit,
    onRemove: (String) -> Unit,
    onCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { item ->
                CartItem(
                    item = item,
                    onQuantityChange = { newQuantity ->
                        onQuantityChange(item.variantSku, newQuantity)
                    },
                    onRemove = { onRemove(item.variantSku) }
                )
            }
        }

        Surface(
            tonalElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.section_subtotal),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "${String.format("%.2f", subtotal)} VND",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onCheckoutClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = items.isNotEmpty()
                ) {
                    Text(text = stringResource(id = R.string.btn_checkout))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLight() {
    VendozaTheme(darkTheme = false) {
        CartContent(
            items = sampleItems,
            subtotal = 0.0,
            onQuantityChange = { _, _ -> },
            onRemove = {},
            onCheckoutClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenDark() {
    VendozaTheme(darkTheme = true) {
        CartContent(
            items = sampleItems,
            subtotal = 0.0,
            onQuantityChange = { _, _ -> },
            onRemove = {},
            onCheckoutClick = {}
        )
    }
}