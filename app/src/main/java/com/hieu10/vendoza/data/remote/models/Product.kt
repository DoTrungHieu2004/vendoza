package com.hieu10.vendoza.data.remote.models

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("_id")
    val id: String,
    @SerializedName("category_id")
    val categoryId: CategorySummary,
    val name: String,
    val description: String?,
    val brand: String?,
    @SerializedName("base_image")
    val baseImage: String?,
    @SerializedName("avg_rating")
    val avgRating: Double,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("created_at")
    val createdAt: String,
    val variants: List<ProductVariant>? = null
)