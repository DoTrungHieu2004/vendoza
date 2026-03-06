package com.hieu10.vendoza.data.remote.models.request

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val phone: String,
    val password: String
)