package com.marcpetit.minddenmvvm.data.products

import com.marcpetit.minddenmvvm.domain.DomainItem
import com.marcpetit.minddenmvvm.domain.products.Product
import com.marcpetit.minddenmvvm.domain.products.ProductType
import com.marcpetit.minddenmvvm.domain.products.ProductsRepository
import javax.inject.Inject

private const val MEN_CLOTHING = "men's clothing"
private const val WOMEN_CLOTHING = "women's clothing"
private const val JEWELRY = "jewelery"
class ProductsRepositoryImpl @Inject constructor(
    private val productsApi: ProductsAPI
): ProductsRepository {

    override suspend fun getProducts(productType: ProductType): DomainItem<List<Product>?> {
        val response = when (productType) {
            ProductType.MEN -> productsApi.getMenClothing()
            ProductType.WOMEN -> productsApi.getWomenClothing()
            ProductType.JEWELRY -> productsApi.getJewelry()
            ProductType.UNKNOWN -> productsApi.getAllClothing()
        }
        return if (response.isSuccessful) {
            response.body()?.run {
                DomainItem(
                    data = this.map {
                        Product(
                            it.id,
                            it.title,
                            it.price,
                            it.description,
                            getProductType(it.category),
                            it.image
                        )
                    }
                )
            } ?: run {
                DomainItem(
                    data = null,
                    error = response.message()
                )
            }

        } else {
            DomainItem(
                data = null,
                error = response.message()
            )
        }
    }

    private fun getProductType(category: String): ProductType {
        return when (category) {
            MEN_CLOTHING -> ProductType.MEN
            WOMEN_CLOTHING -> ProductType.WOMEN
            JEWELRY -> ProductType.JEWELRY
            else -> ProductType.UNKNOWN
        }
    }

}