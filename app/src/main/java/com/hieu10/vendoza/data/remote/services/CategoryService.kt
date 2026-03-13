package com.hieu10.vendoza.data.remote.services

import com.hieu10.vendoza.data.remote.models.Category
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryService {
    @GET("api/categories")
    suspend fun getCategories(
        @Query("tree") tree: Boolean = false
    ): List<Category>

    @GET("api/categories/{identifier}")
    suspend fun getCategoryByIdOrSlug(
        @Path("identifier") identifier: String
    ): Category
}