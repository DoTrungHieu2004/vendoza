package com.hieu10.vendoza.data.remote.services

import com.hieu10.vendoza.data.remote.models.Product
import com.hieu10.vendoza.data.remote.models.response.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("api/products")
    suspend fun getProducts(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("q") query: String = "",
        @Query("sortBy") sortBy: String = "created_at",
        @Query("order") order: String = "desc"
    ): ProductsResponse

    @GET("api/products/{id}")
    suspend fun getProductById(@Path("id") productId: String): Product
}