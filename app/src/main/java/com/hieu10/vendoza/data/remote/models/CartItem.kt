package com.hieu10.vendoza.data.remote.models

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.hieu10.vendoza.utils.Decimal128Deserializer

data class CartItem(
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("variant_sku")
    val variantSku: String,
    val quantity: Int,
    @JsonAdapter(Decimal128Deserializer::class)
    @SerializedName("price_snapshot")
    val priceSnapshot: Double,

    // Enriched fields (from backend)
    @SerializedName("product_name")
    val productName: String? = null,
    @SerializedName("product_image")
    val productImage: String? = null,
    @SerializedName("variant_attributes")
    val variantAttributes: Map<String, Any>? = null,
    @SerializedName("current_stock")
    val currentStock: Int? = null,
    val unavailable: Boolean? = null
)