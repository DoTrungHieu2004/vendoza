package com.hieu10.vendoza.data.repository

import com.hieu10.vendoza.data.local.TokenManager
import com.hieu10.vendoza.data.remote.models.User
import com.hieu10.vendoza.data.remote.models.request.LoginRequest
import com.hieu10.vendoza.data.remote.models.request.RegisterRequest
import com.hieu10.vendoza.data.remote.services.AuthService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class AuthRepository(
    private val authService: AuthService,
    private val tokenManager: TokenManager
) {
    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val message: String, val code: Int? = null) : Result<Nothing>()
        object Loading : Result<Nothing>()
    }

    suspend fun register(request: RegisterRequest): Result<User> {
        return try {
            val response = authService.register(request)
            tokenManager.saveToken(response.token)
            Result.Success(response.user)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.Error(errorBody ?: "Registration failed", e.code())
        } catch (e: IOException) {
            Result.Error("Network error: ${e.message}")
        } catch (e: Exception) {
            Result.Error("Unexpected error: ${e.message}")
        }
    }

    suspend fun login(request: LoginRequest): Result<User> {
        return try {
            val response = authService.login(request)
            tokenManager.saveToken(response.token)
            Result.Success(response.user)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.Error(errorBody ?: "Login failed", e.code())
        } catch (e: IOException) {
            Result.Error("Network error: ${e.message}")
        } catch (e: Exception) {
            Result.Error("Unexpected error: ${e.message}")
        }
    }

    suspend fun getCurrentUser(): Result<User> {
        return try {
            val response = authService.getMe()
            Result.Success(response.user)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                // Unauthorized - clear token and treated as not logged in
                tokenManager.clearToken()
                Result.Error("Session expired", 401)
            } else {
                val errorBody = e.response()?.errorBody()?.string()
                Result.Error(errorBody ?: "Failed to fetch user", e.code())
            }
        } catch (e: IOException) {
            Result.Error("Network error: ${e.message}")
        } catch (e: Exception) {
            Result.Error("Unexpected error: ${e.message}")
        }
    }

    suspend fun logout() {
        tokenManager.clearToken()
    }

    suspend fun isLoggedIn(): Boolean {
        return tokenManager.token.map { it != null }.first()
    }
}