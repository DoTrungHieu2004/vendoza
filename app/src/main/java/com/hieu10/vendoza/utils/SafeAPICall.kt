package com.hieu10.vendoza.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

suspend fun <T> safeAPICall(
    apiCall: suspend () -> T
): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            Result.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = throwable.response()?.errorBody()?.string()
                    val message = parseErrorMessage(errorResponse) ?: throwable.message()
                    Result.Error(message, code)
                }
                is IOException -> Result.Error("Network error. Please check your connection.")
                else -> Result.Error("Unexpected error: ${throwable.message}")
            }
        }
    }
}

// Helper to extract message from error response body
private fun parseErrorMessage(json: String?): String? {
    return try {
        val regex = "\"message\":\"(.*?)\"".toRegex()
        regex.find(json ?: "")?.groupValues?.get(1)
    } catch (e: Exception) {
        null
    }
}