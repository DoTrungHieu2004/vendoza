package com.hieu10.vendoza.data.remote.models

import com.google.gson.annotations.SerializedName

data class CategorySummary(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val slug: String
)