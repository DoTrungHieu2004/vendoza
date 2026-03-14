package com.hieu10.vendoza.data.remote

import com.hieu10.vendoza.BuildConfig
import com.hieu10.vendoza.data.interceptors.AuthInterceptor
import com.hieu10.vendoza.data.local.TokenManager
import com.hieu10.vendoza.data.remote.services.AuthService
import com.hieu10.vendoza.data.remote.services.CategoryService
import com.hieu10.vendoza.data.remote.services.ProductService
import com.hieu10.vendoza.data.repository.CategoryRepository
import com.hieu10.vendoza.data.repository.ProductRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private lateinit var tokenManager: TokenManager

    fun initialize(tokenManager: TokenManager) {
        this.tokenManager = tokenManager
    }

    private fun requireInitialized() {
        if (!::tokenManager.isInitialized) {
            throw IllegalStateException("ApiClient must be initialized before use")
        }
    }

    private val client: OkHttpClient by lazy {
        requireInitialized()
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(tokenManager))
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
            .build()
    }

    private val retrofit: Retrofit by lazy {
        requireInitialized()
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API services
    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val categoryService: CategoryService by lazy {
        retrofit.create(CategoryService::class.java)
    }

    val productService: ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }

    // Repositories
    val categoryRepository: CategoryRepository by lazy {
        CategoryRepository(categoryService)
    }

    val productRepository: ProductRepository by lazy {
        ProductRepository(productService)
    }
}