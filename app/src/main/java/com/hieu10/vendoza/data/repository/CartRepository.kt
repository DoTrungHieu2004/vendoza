package com.hieu10.vendoza.data.repository

import com.hieu10.vendoza.data.remote.models.Cart
import com.hieu10.vendoza.data.remote.models.request.AddItemRequest
import com.hieu10.vendoza.data.remote.models.request.UpdateItemRequest
import com.hieu10.vendoza.data.remote.services.CartService
import java.io.IOException

class CartRepository(
    private val cartService: CartService
) {
    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val message: String, val code: Int? = null) : Result<Nothing>()
        object Loading : Result<Nothing>()
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.Success(apiCall())
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.Error(errorBody ?: "Request failed", e.code())
        } catch (e: IOException) {
            Result.Error("Network error: ${e.message}")
        } catch (e: Exception) {
            Result.Error("Unexpected error: ${e.message}")
        }
    }

    suspend fun getCart(): Result<Cart> = safeApiCall { cartService.getCart() }

    suspend fun addItem(variantSku: String, quantity: Int): Result<Cart> =
        safeApiCall { cartService.addItem(AddItemRequest(variantSku, quantity)) }

    suspend fun updateItem(variantSku: String, quantity: Int): Result<Cart> =
        safeApiCall { cartService.updateItem(variantSku, UpdateItemRequest(quantity)) }

    suspend fun removeItem(variantSku: String): Result<Cart> =
        safeApiCall { cartService.removeItem(variantSku) }

    suspend fun clearCart(): Result<Cart> = safeApiCall { cartService.clearCart() }
}