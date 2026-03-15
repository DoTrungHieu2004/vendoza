package com.hieu10.vendoza.data.repository

import com.hieu10.vendoza.data.remote.models.Product
import com.hieu10.vendoza.data.remote.models.response.ProductsResponse
import com.hieu10.vendoza.data.remote.services.ProductService
import retrofit2.HttpException
import java.io.IOException

class ProductRepository(
    private val productService: ProductService
) {
    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val message: String, val code: Int? = null) : Result<Nothing>()
        object Loading : Result<Nothing>()
    }

    suspend fun getProducts(
        page: Int = 1,
        limit: Int = 10,
        query: String = "",
        sortBy: String = "created_at",
        order: String = "desc",
        categoryId: String? = null
    ): Result<ProductsResponse> {
        return try {
            val response = productService.getProducts(page, limit, query, sortBy, order, categoryId)
            Result.Success(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.Error(errorBody ?: "Failed to load products", e.code())
        } catch (e: IOException) {
            Result.Error("Network error: ${e.message}")
        } catch (e: Exception) {
            Result.Error("Unexpected error: ${e.message}")
        }
    }

    suspend fun getProductById(productId: String): Result<Product> {
        return try {
            val response = productService.getProductById(productId)
            Result.Success(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.Error(errorBody ?: "Product not found", e.code())
        } catch (e: IOException) {
            Result.Error("Network error: ${e.message}")
        } catch (e: Exception) {
            Result.Error("Unexpected error: ${e.message}")
        }
    }
}