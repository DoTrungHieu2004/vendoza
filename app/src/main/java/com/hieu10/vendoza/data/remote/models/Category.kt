package com.hieu10.vendoza.data.remote.models

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val slug: String,
    @SerializedName("parent_id")
    val parentId: String?,
    val path: String,
    val level: Int,
    val children: List<Category>? = null    // only present in tree response
)