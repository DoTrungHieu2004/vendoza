package com.hieu10.vendoza.data.remote.dto.request

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val phone: String? = null
)