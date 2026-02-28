package com.hieu10.vendoza.data.repository

import com.hieu10.vendoza.data.local.TokenManager
import com.hieu10.vendoza.data.remote.dto.User
import com.hieu10.vendoza.data.remote.dto.request.LoginRequest
import com.hieu10.vendoza.data.remote.dto.request.RegisterRequest
import com.hieu10.vendoza.data.remote.dto.response.AuthResponse
import com.hieu10.vendoza.data.remote.service.APIService
import com.hieu10.vendoza.utils.Result
import com.hieu10.vendoza.utils.safeAPICall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class AuthRepository(
    private val apiService: APIService,
    private val tokenManager: TokenManager
) {
    // Register a new user
    suspend fun register(request: RegisterRequest): Result<AuthResponse> {
        return safeAPICall {
            apiService.register(request).also { response ->
                // Automatically save token on successful registration
                tokenManager.saveToken(response.token)
            }
        }
    }

    // Login user
    suspend fun login(request: LoginRequest): Result<AuthResponse> {
        return safeAPICall {
            apiService.login(request).also { response ->
                tokenManager.saveToken(response.token)
            }
        }
    }

    // Get current user profile (requires valid token)
    suspend fun getMe(): Result<User> {
        return safeAPICall {
            val response = apiService.getMe()
            response.user
        }
    }

    // Logout - simply clear token
    suspend fun logout() {
        tokenManager.clearToken()
    }

    // Check if user is logged in (token exists)
    suspend fun isLoggedIn(): Boolean {
        return tokenManager.token.firstOrNull() != null
    }

    // Observe token state (useful for navigation)
    fun observeToken(): Flow<String?> = tokenManager.token
}