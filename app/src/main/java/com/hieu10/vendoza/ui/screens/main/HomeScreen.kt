package com.hieu10.vendoza.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hieu10.vendoza.R
import com.hieu10.vendoza.ui.components.CategoryChips
import com.hieu10.vendoza.ui.components.HeroBanner
import com.hieu10.vendoza.ui.components.bg.AppBG
import com.hieu10.vendoza.ui.theme.VendozaTheme

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeroBanner(modifier = Modifier.padding(8.dp))
        }

        item {
            Text(
                text = stringResource(id = R.string.headline_shop_by_category),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        item {
            CategoryChips(
                onCategoryClick = { categoryId ->
                    // TODO: navigate to product list filtered by category
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLight() {
    VendozaTheme(darkTheme = false) {
        AppBG {
            HomeScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenDark() {
    VendozaTheme(darkTheme = true) {
        AppBG {
            HomeScreen()
        }
    }
}