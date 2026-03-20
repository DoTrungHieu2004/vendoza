package com.hieu10.vendoza.data.remote.models

import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("cart_id")
    val cartId: String,
    val items: List<CartItem>,
    val subtotal: Double,
    @SerializedName("item_count")
    val itemCount: Int,
    @SerializedName("expires_at")
    val expiresAt: String? = null,
    val status: String? = null
)