package com.marcpetit.minddenmvvm.data.products

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marcpetit.minddenmvvm.domain.DomainItem
import com.marcpetit.minddenmvvm.domain.products.Product
import com.marcpetit.minddenmvvm.domain.products.ProductType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

private const val MEN_CLOTHING = "men's clothing"
private const val WOMEN_CLOTHING = "women's clothing"
private const val JEWELRY = "jewelery"

class ProductsRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val productsApi: ProductsAPI = mockk()
    private lateinit var productsRepository: ProductsRepositoryImpl

    private val clothingList = listOf(
        ProductApiResponse(
            id = 0,
            title = "title1",
            price = 10.0,
            description = "description1",
            category = "category1",
            image = "image1",
        ),
        ProductApiResponse(
            id = 0,
            title = "title2",
            price = 20.0,
            description = "description2",
            category = MEN_CLOTHING,
            image = "image2",
        ),
        ProductApiResponse(
            id = 0,
            title = "title2",
            price = 20.0,
            description = "description2",
            category = WOMEN_CLOTHING,
            image = "image2",
        ),
        ProductApiResponse(
            id = 0,
            title = "title2",
            price = 20.0,
            description = "description2",
            category = JEWELRY,
            image = "image2",
        )
    )

    @Before
    fun setUp() {
        productsRepository = ProductsRepositoryImpl(productsApi)
    }

    @Test
    fun `when getProducts and productType is MEN OK then call men clothing endpoint and return mapped list`() {
        val list: DomainItem<List<Product>?>
        // Given
        coEvery { productsApi.getMenClothing() } returns Response.success(clothingList)

        // When
        runBlocking {
            list = productsRepository.getProducts(ProductType.MEN)
        }

        // Then
        coVerify(exactly = 1) { productsApi.getMenClothing() }
        coVerify(exactly = 0) { productsApi.getWomenClothing() }
        coVerify(exactly = 0) { productsApi.getJewelry() }
        coVerify(exactly = 0) { productsApi.getAllClothing() }
        assertEquals(list, DomainItem<List<Product>?>(
            clothingList.map {
                Product(
                    id = it.id,
                    title = it.title,
                    price = it.price,
                    description = it.description,
                    category = getProductType(it.category),
                    image = it.image,
                )
            }
        ))
    }

    @Test
    fun `when getProducts and productType is WOMEN OK then call women clothing endpoint and return mapped list`() {
        val list: DomainItem<List<Product>?>
        // Given
        coEvery { productsApi.getWomenClothing() } returns Response.success(clothingList)

        // When
        runBlocking {
            list = productsRepository.getProducts(ProductType.WOMEN)
        }

        // Then
        coVerify(exactly = 0) { productsApi.getMenClothing() }
        coVerify(exactly = 1) { productsApi.getWomenClothing() }
        coVerify(exactly = 0) { productsApi.getJewelry() }
        coVerify(exactly = 0) { productsApi.getAllClothing() }
        assertEquals(list, DomainItem<List<Product>?>(
            clothingList.map {
                Product(
                    id = it.id,
                    title = it.title,
                    price = it.price,
                    description = it.description,
                    category = getProductType(it.category),
                    image = it.image,
                )
            }
        ))
    }

    @Test
    fun `when getProducts and productType is JEWELRY OK then call jewelry clothing endpoint and return mapped list`() {
        val list: DomainItem<List<Product>?>
        // Given
        coEvery { productsApi.getJewelry() } returns Response.success(clothingList)

        // When
        runBlocking {
            list = productsRepository.getProducts(ProductType.JEWELRY)
        }

        // Then
        coVerify(exactly = 0) { productsApi.getMenClothing() }
        coVerify(exactly = 0) { productsApi.getWomenClothing() }
        coVerify(exactly = 1) { productsApi.getJewelry() }
        coVerify(exactly = 0) { productsApi.getAllClothing() }
        assertEquals(list, DomainItem<List<Product>?>(
            clothingList.map {
                Product(
                    id = it.id,
                    title = it.title,
                    price = it.price,
                    description = it.description,
                    category = getProductType(it.category),
                    image = it.image,
                )
            }
        ))
    }

    @Test
    fun `when getProducts and productType is UNKNOWN OK then call all clothing endpoint and return mapped list`() {
        val list: DomainItem<List<Product>?>
        // Given
        coEvery { productsApi.getAllClothing() } returns Response.success(clothingList)

        // When
        runBlocking {
            list = productsRepository.getProducts(ProductType.UNKNOWN)
        }

        // Then
        coVerify(exactly = 0) { productsApi.getMenClothing() }
        coVerify(exactly = 0) { productsApi.getWomenClothing() }
        coVerify(exactly = 0) { productsApi.getJewelry() }
        coVerify(exactly = 1) { productsApi.getAllClothing() }
        assertEquals(list, DomainItem<List<Product>?>(
            clothingList.map {
                Product(
                    id = it.id,
                    title = it.title,
                    price = it.price,
                    description = it.description,
                    category = getProductType(it.category),
                    image = it.image,
                )
            }
        ))
    }

    @Test
    fun `when getProducts and productType (any) OK but body is null then return DomainItem with OK error message`() {
        val list: DomainItem<List<Product>?>
        // Given
        coEvery { productsApi.getMenClothing() } returns Response.success(null)

        // When
        runBlocking {
            list = productsRepository.getProducts(ProductType.MEN)
        }

        // Then
        coVerify(exactly = 1) { productsApi.getMenClothing() }
        coVerify(exactly = 0) { productsApi.getWomenClothing() }
        coVerify(exactly = 0) { productsApi.getJewelry() }
        coVerify(exactly = 0) { productsApi.getAllClothing() }
        assertEquals(list, DomainItem(null, "OK"))
    }

    @Test
    fun `when getProducts and productType (any) KO then return DomainItem with KO error message`() {
        val list: DomainItem<List<Product>?>
        // Given
        coEvery { productsApi.getMenClothing() } returns Response.error(404, "Error".toResponseBody())

        // When
        runBlocking {
            list = productsRepository.getProducts(ProductType.MEN)
        }

        // Then
        coVerify(exactly = 1) { productsApi.getMenClothing() }
        coVerify(exactly = 0) { productsApi.getWomenClothing() }
        coVerify(exactly = 0) { productsApi.getJewelry() }
        coVerify(exactly = 0) { productsApi.getAllClothing() }
        assertEquals(list, DomainItem(null, "Response.error()"))
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