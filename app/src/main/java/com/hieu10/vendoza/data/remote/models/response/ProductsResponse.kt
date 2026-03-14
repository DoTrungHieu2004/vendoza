package com.hieu10.vendoza.data.remote.models.response

import com.hieu10.vendoza.data.remote.models.Pagination
import com.hieu10.vendoza.data.remote.models.Product

data class ProductsResponse(
    val products: List<Product>,
    val pagination: Pagination
)