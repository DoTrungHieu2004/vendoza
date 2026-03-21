package com.hieu10.vendoza.data.sample

import com.hieu10.vendoza.ui.components.CartItemUI

val sampleItems = listOf(
    CartItemUI(
        id = "1",
        variantSku = "SKU001",
        imageUrl = null,
        name = "Wireless Headphones",
        attributes = "Color: Black, Size: One Size",
        price = 89.99,
        quantity = 2,
        stock = 10
    ),
    CartItemUI(
        id = "2",
        variantSku = "SKU002",
        imageUrl = null,
        name = "Smart Watch",
        attributes = "Color: Silver, Band: Sport",
        price = 199.99,
        quantity = 1,
        stock = 5
    )
)