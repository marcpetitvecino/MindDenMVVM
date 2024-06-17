package com.marcpetit.minddenmvvm.domain.products

import com.marcpetit.minddenmvvm.domain.DomainItem

interface ProductsRepository {
    suspend fun getProducts(productType: ProductType): DomainItem<List<Product>?>
}