package com.marcpetit.minddenmvvm.data.products

import retrofit2.Response
import retrofit2.http.GET

interface ProductsAPI {

    @GET("products/category/jewelery")
    suspend fun getJewelry(): Response<List<ProductApiResponse>>

    @GET("products/category/women's clothing")
    suspend fun getWomenClothing(): Response<List<ProductApiResponse>>

    @GET("products/category/men's clothing")
    suspend fun getMenClothing(): Response<List<ProductApiResponse>>

    @GET("products")
    suspend fun getAllClothing(): Response<List<ProductApiResponse>>
}