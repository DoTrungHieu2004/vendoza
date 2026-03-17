package com.hieu10.vendoza.data.remote.models

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.hieu10.vendoza.utils.Decimal128Deserializer

data class ProductVariant(
    @SerializedName("_id")
    val id: String,
    @SerializedName("product_id")
    val productId: String,
    val SKU: String,
    @JsonAdapter(Decimal128Deserializer::class)
    val price: Double,
    @SerializedName("stock_quantity")
    val stockQuantity: Int,
    @SerializedName("low_stock_threshold")
    val lowStockThreshold: Int,
    val attributes: Map<String, Any>? = emptyMap()
)