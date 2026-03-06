package com.hieu10.vendoza.data.remote.services

import com.hieu10.vendoza.data.remote.models.request.LoginRequest
import com.hieu10.vendoza.data.remote.models.request.RegisterRequest
import com.hieu10.vendoza.data.remote.models.response.AuthResponse
import com.hieu10.vendoza.data.remote.models.response.MeResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @GET("auth/me")
    suspend fun getMe(): MeResponse
}