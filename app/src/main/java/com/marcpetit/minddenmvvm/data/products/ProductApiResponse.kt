package com.marcpetit.minddenmvvm.data.products

import com.google.gson.annotations.SerializedName

data class ProductApiResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Double,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("image") val image: String
)