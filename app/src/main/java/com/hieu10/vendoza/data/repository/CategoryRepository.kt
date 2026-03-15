package com.hieu10.vendoza.data.repository

import com.hieu10.vendoza.data.remote.models.Category
import com.hieu10.vendoza.data.remote.services.CategoryService
import retrofit2.HttpException
import java.io.IOException

class CategoryRepository(
    private val categoryService: CategoryService
) {
    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val message: String, val code: Int? = null) : Result<Nothing>()
        object Loading : Result<Nothing>()
    }

    suspend fun getCategories(tree: Boolean = false): Result<List<Category>> {
        return try {
            val response = categoryService.getCategories(tree)
            Result.Success(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.Error(errorBody ?: "Failed to load categories", e.code())
        } catch (e: IOException) {
            Result.Error("Network error: ${e.message}")
        } catch (e: Exception) {
            Result.Error("Unexpected error: ${e.message}")
        }
    }

    suspend fun getCategoryByIdOrSlug(identifier: String?): Result<Category> {
        return try {
            val response = categoryService.getCategoryByIdOrSlug(identifier)
            Result.Success(response)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Result.Error(errorBody ?: "Category not found", e.code())
        } catch (e: IOException) {
            Result.Error("Network error: ${e.message}")
        } catch (e: Exception) {
            Result.Error("Unexpected error: ${e.message}")
        }
    }
}