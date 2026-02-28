package com.hieu10.vendoza.data.remote.interceptor

import com.hieu10.vendoza.data.local.TokenManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        // Retrieve token synchronously (runBlocking is acceptable here as it's a short operation)
        runBlocking {
            tokenManager.token.firstOrNull()?.let { token ->
                request.addHeader("Authorization", "Bearer $token")
            }
        }

        return chain.proceed(request.build())
    }
}