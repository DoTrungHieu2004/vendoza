package com.hieu10.vendoza.data.remote.dto

import com.google.gson.annotations.SerializedName

data class User(
    val id: String,
    val username: String,
    val email: String,
    val role: String,
    @SerializedName("avatar_url") val avatarUrl: String?,
    val phone: String?,
    @SerializedName("created_at") val createdAt: String // ISO date string
)