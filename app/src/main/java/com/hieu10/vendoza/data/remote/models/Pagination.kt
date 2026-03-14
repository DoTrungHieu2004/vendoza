package com.hieu10.vendoza.data.remote.models

data class Pagination(
    val page: Int,
    val limit: Int,
    val total: Int,
    val pages: Int
)