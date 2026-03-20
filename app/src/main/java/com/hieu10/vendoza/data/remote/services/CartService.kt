package com.hieu10.vendoza.data.remote.services

import com.hieu10.vendoza.data.remote.models.Cart
import com.hieu10.vendoza.data.remote.models.request.AddItemRequest
import com.hieu10.vendoza.data.remote.models.request.UpdateItemRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CartService {
    @GET("api/cart")
    suspend fun getCart(): Cart

    @POST("api/cart/items")
    suspend fun addItem(@Body item: AddItemRequest): Cart

    @PUT("api/cart/items/{variantSku}")
    suspend fun updateItem(
        @Path("variantSku") variantSku: String,
        @Body request: UpdateItemRequest
    ): Cart

    @DELETE("api/cart/items/{variantSku}")
    suspend fun removeItem(@Path("variantSku") variantSku: String): Cart

    @DELETE("cart")
    suspend fun clearCart(): Cart
}