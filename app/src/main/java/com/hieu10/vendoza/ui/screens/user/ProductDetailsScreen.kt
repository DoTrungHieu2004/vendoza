package com.hieu10.vendoza.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.hieu10.vendoza.R
import com.hieu10.vendoza.data.remote.ApiClient
import com.hieu10.vendoza.data.remote.models.CategorySummary
import com.hieu10.vendoza.data.remote.models.Product
import com.hieu10.vendoza.ui.components.RatingChip
import com.hieu10.vendoza.ui.components.VariantSelector
import com.hieu10.vendoza.ui.theme.VendozaTheme
import com.hieu10.vendoza.viewmodel.ProductDetailsViewModel
import com.hieu10.vendoza.viewmodel.factory.ProductDetailsVMFactory
import com.hieu10.vendoza.viewmodel.state.ProductDetailsUIState

@Composable
fun ProductDetailsScreen(
    productId: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: ProductDetailsViewModel = viewModel(
        factory = ProductDetailsVMFactory(
            productRepository = ApiClient.productRepository,
            productId = productId
        )
    )
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is ProductDetailsUIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is ProductDetailsUIState.Success -> {
            val product = (uiState as ProductDetailsUIState.Success).product
            ProductDetailsContent(
                product = product,
                onNavigateBack = onNavigateBack,
                modifier = modifier
            )
        }
        is ProductDetailsUIState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.error, uiState as ProductDetailsUIState.Error),
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.loadProduct() }) {
                    Text(text = stringResource(id = R.string.btn_retry))
                }
            }
        }
    }
}

@Composable
private fun ProductDetailsContent(
    product: Product,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedVariant by remember { mutableStateOf(product.variants?.firstOrNull()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        // Product image with overlays
        Box(
            modifier = Modifier.fillMaxWidth().aspectRatio(1f)
        ) {
            AsyncImage(
                model = product.baseImage ?: R.drawable.product_placeholder,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Surface(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.extraSmall),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                tonalElevation = 4.dp
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.cd_back),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            RatingChip(
                rating = product.avgRating,
                reviewCount = product.variants?.size ?: 0,
                modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
            )
        }

        // Product details section
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            product.brand?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            val price = selectedVariant?.price ?: product.variants?.firstOrNull()?.price ?: 0.0
            Text(
                text = "${String.format("%.2f", price)} VND",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (product.variants != null && product.variants.size > 1) {
                VariantSelector(
                    variants = product.variants,
                    initialVariant = selectedVariant,
                    onVariantSelected = { selectedVariant = it },
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = stringResource(id = R.string.section_description),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            product.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* TODO: Add to cart */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = stringResource(id = R.string.btn_add_to_cart))
            }
        }
    }
}

private val sampleProduct = Product(
    id = "1",
    categoryId = CategorySummary("1", "Category Name", "category-name"),
    name = "Product Name",
    description = "This is a placeholder product description. It should be fetched from the backend along with all product details.",
    brand = "Brand",
    baseImage = "",
    avgRating = 4.5,
    isActive = true,
    createdAt = ""
)

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLight() {
    VendozaTheme(darkTheme = false) {
        ProductDetailsContent(
            product = sampleProduct,
            onNavigateBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenDark() {
    VendozaTheme(darkTheme = true) {
        ProductDetailsContent(
            product = sampleProduct,
            onNavigateBack = {}
        )
    }
}