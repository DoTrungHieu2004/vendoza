package com.hieu10.vendoza.data.remote.models.response

import com.hieu10.vendoza.data.remote.models.User

data class AuthResponse(
    val message: String,
    val user: User,
    val token: String
)

data class MeResponse(
    val user: User
)