package com.hieu10.vendoza.data.remote.service

import com.hieu10.vendoza.data.remote.dto.request.LoginRequest
import com.hieu10.vendoza.data.remote.dto.request.RegisterRequest
import com.hieu10.vendoza.data.remote.dto.response.AuthResponse
import com.hieu10.vendoza.data.remote.dto.response.MeResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    // ========== AUTH ==========
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @GET("auth/me")
    suspend fun getMe(): MeResponse
}