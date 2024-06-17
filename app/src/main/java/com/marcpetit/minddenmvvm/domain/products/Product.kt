package com.marcpetit.minddenmvvm.domain.products

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product (
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: ProductType,
    val image: String
): Parcelable