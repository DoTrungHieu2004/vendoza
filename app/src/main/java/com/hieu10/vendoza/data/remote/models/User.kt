package com.hieu10.vendoza.data.remote.models

import com.google.gson.annotations.SerializedName

data class User(
    val id: String,
    val username: String,
    val email: String,
    val phone: String,
    val role: Role,
    val status: Status? = null,
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    val addresses: List<String> = emptyList(),
    val preferences: Map<String, Any>? = null,
    @SerializedName("last_login_at")
    val lastLoginAt: String? = null,
    @SerializedName("created_at")
    val createdAt: String
) {
    enum class Role { user, moderator, admin }
    enum class Status { active, inactive, suspended }
}