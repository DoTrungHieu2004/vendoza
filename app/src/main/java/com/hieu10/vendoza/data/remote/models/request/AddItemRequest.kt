package com.hieu10.vendoza.data.remote.models.request

import com.google.gson.annotations.SerializedName

data class AddItemRequest(
    @SerializedName("variant_sku")
    val variantSku: String,
    val quantity: Int
)