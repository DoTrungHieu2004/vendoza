package com.hieu10.vendoza.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.vendoza.ui.theme.VendozaTheme

data class CategoryChipItem(
    val id: String,
    val name: String
)

@Composable
fun CategoryChips(
    categories: List<CategoryChipItem> = defaultCategories,
    onCategoryClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories) { category ->
            AssistChip(
                onClick = { onCategoryClick(category.id) },
                label = { Text(text = category.name) },
                modifier = Modifier.wrapContentWidth()
            )
        }
    }
}

// Dummy data for preview
private val defaultCategories = listOf(
    CategoryChipItem("1", "Electronics"),
    CategoryChipItem("2", "Fashion"),
    CategoryChipItem("3", "Home & Garden"),
    CategoryChipItem("4", "Sports"),
    CategoryChipItem("5", "Books"),
    CategoryChipItem("6", "Toys")
)

@Preview(showBackground = true)
@Composable
private fun PreviewCategoryChipLight() {
    VendozaTheme(darkTheme = false) {
        CategoryChips()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCategoryChipDark() {
    VendozaTheme(darkTheme = true) {
        CategoryChips()
    }
}
