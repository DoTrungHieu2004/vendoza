package com.hieu10.vendoza.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.vendoza.data.remote.models.ProductVariant
import com.hieu10.vendoza.ui.theme.VendozaTheme

@Composable
fun VariantSelector(
    variants: List<ProductVariant>,
    initialVariant: ProductVariant? = variants.firstOrNull(),
    onVariantSelected: (ProductVariant) -> Unit,
    modifier: Modifier = Modifier
) {
    // Extract all unique attribute keys from variants
    val attributeKeys = remember(key1 = variants) {
        variants.flatMap { it.attributes?.keys ?: emptySet() }.distinct()
    }

    // State to track selected value for each attribute key
    val selectedAttributes = remember(key1 = initialVariant) {
        mutableStateMapOf<String, String>().apply {
            initialVariant?.attributes?.forEach { (key, value) ->
                if (value is String) {
                    put(key, value)
                }
            }
        }
    }

    // Find the variant that matches all selected attributes
    val matchingVariant = remember(key1 = selectedAttributes, key2 = variants) {
        variants.firstOrNull { variant ->
            attributeKeys.all { key ->
                variant.attributes?.get(key) == selectedAttributes[key]
            }
        }
    }

    // Notify parent when matching variant changes
    LaunchedEffect(key1 = matchingVariant) {
        matchingVariant?.let { onVariantSelected(it) }
    }

    if (attributeKeys.isEmpty()) return

    Column(modifier = modifier) {
        attributeKeys.forEach { key ->
            // Get all unique values for this key across variants
            val values = remember(key1 = key, key2 = variants) {
                variants.mapNotNull { it.attributes?.get(key) as? String }.distinct()
            }

            Text(
                text = key.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                values.forEach { value ->
                    val isSelected = selectedAttributes[key] == value
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedAttributes[key] = value },
                        label = { Text(text = value) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }
        }
    }
}

private val sampleVariants = listOf(
    ProductVariant(id = "1", productId = "p1", SKU = "SKU-101", price = 100.00, stockQuantity = 50, lowStockThreshold = 5),
    ProductVariant(id = "2", productId = "p1", SKU = "SKU-102", price = 110.00, stockQuantity = 55, lowStockThreshold = 5)
)

@Preview(showBackground = true)
@Composable
private fun PreviewSelectorLight() {
    VendozaTheme(darkTheme = false) {
        VariantSelector(
            variants = sampleVariants,
            onVariantSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSelectorDark() {
    VendozaTheme(darkTheme = true) {
        VariantSelector(
            variants = sampleVariants,
            onVariantSelected = {}
        )
    }
}