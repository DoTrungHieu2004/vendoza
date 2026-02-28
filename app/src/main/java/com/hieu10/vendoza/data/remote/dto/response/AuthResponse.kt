package com.hieu10.vendoza.data.remote.dto.response

import com.hieu10.vendoza.data.remote.dto.User

data class AuthResponse(
    val message: String,
    val user: User,
    val token: String
)