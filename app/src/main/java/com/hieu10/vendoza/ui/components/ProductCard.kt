package com.hieu10.vendoza.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hieu10.vendoza.R
import com.hieu10.vendoza.data.remote.models.CategorySummary
import com.hieu10.vendoza.data.remote.models.Product
import com.hieu10.vendoza.ui.theme.VendozaTheme

@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            // Product image
            AsyncImage(
                model = product.baseImage ?: R.drawable.product_placeholder,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            // Product info
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${product.variants?.firstOrNull()?.price ?: 0} VND",
                        style = MaterialTheme.typography.titleLarge
                    )

                    if (product.avgRating > 0) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color.Yellow
                            )
                            Text(
                                text = "${product.avgRating}",
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                    }
                }
            }
        }
    }
}

private val productSample = Product(
    id = "123",
    categoryId = CategorySummary(id = "1", name = "Cat", slug = "cat"),
    name = "Product Name",
    description = "Product Description",
    brand = "Brand",
    baseImage = "https://placehold.co/600x400",
    avgRating = 5.0,
    isActive = true,
    createdAt = "2023-06-01T12:00:00Z"
)

@Preview(showBackground = true)
@Composable
private fun PreviewProductCardLight() {
    VendozaTheme(darkTheme = false) {
        ProductCard(product = productSample, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProductCardDark() {
    VendozaTheme(darkTheme = true) {
        ProductCard(product = productSample, onClick = {})
    }
}