package com.hieu10.vendoza.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hieu10.vendoza.R
import com.hieu10.vendoza.ui.theme.VendozaTheme

data class CartItemUI(
    val id: String,
    val variantSku: String,
    val imageUrl: String?,
    val name: String,
    val attributes: String,
    val price: Double,
    val quantity: Int,
    val stock: Int,
    val unavailable: Boolean = false
)

@Composable
fun CartItem(
    item: CartItemUI,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Image
            AsyncImage(
                model = item.imageUrl ?: R.drawable.product_placeholder,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.attributes,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${String.format("%.2f", item.price)} VND",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                if (item.unavailable) {
                    Text(
                        text = stringResource(id = R.string.label_product_unavailable),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            // Quantity controls and remove
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { onQuantityChange(item.quantity - 1) },
                        enabled = !item.unavailable && item.quantity > 1
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = stringResource(id = R.string.cd_decrease_quantity)
                        )
                    }
                    Text(
                        text = item.quantity.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    IconButton(
                        onClick = { onQuantityChange(item.quantity + 1) },
                        enabled = !item.unavailable && item.quantity < item.stock
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.cd_increase_quantity)
                        )
                    }
                }
                IconButton(
                    onClick = onRemove,
                    enabled = !item.unavailable
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.cd_remove_item),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

private val sampleItem = CartItemUI(
    id = "1",
    variantSku = "SKU001",
    imageUrl = null,
    name = "Wireless Headphones",
    attributes = "Color: Black, Size: One Size",
    price = 450000.00,
    quantity = 2,
    stock = 10
)

@Preview(showBackground = true)
@Composable
private fun PreviewItemLight() {
    VendozaTheme(darkTheme = false) {
        CartItem(
            item = sampleItem,
            onQuantityChange = {},
            onRemove = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewItemDark() {
    VendozaTheme(darkTheme = true) {
        CartItem(
            item = sampleItem,
            onQuantityChange = {},
            onRemove = {}
        )
    }
}