package com.marcpetit.minddenmvvm.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import com.marcpetit.minddenmvvm.MainDispatcherRule
import com.marcpetit.minddenmvvm.domain.DomainItem
import com.marcpetit.minddenmvvm.domain.products.Product
import com.marcpetit.minddenmvvm.domain.products.ProductType
import com.marcpetit.minddenmvvm.domain.products.ProductsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val lifecycleOwner: LifecycleOwner = mockk()

    private val repository: ProductsRepository = mockk()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel(repository)
    }

    val productList = listOf(
        Product(
            id = 0,
            title = "title1",
            price = 10.0,
            description = "description1",
            category = ProductType.MEN,
            image = "image1",
        ),
        Product(
            id = 0,
            title = "title2",
            price = 20.0,
            description = "description2",
            category = ProductType.WOMEN,
            image = "image2",
        ),
    )

    @Test
    fun `when getProducts and response data is not null then update _productList with the result`() {
        // Given
        coEvery { repository.getProducts(any()) } returns DomainItem(productList)

        // When
        TestScope().runTest {
            viewModel.getProducts(ProductType.MEN)
        }

        // Then
        viewModel.productList.observeForever {
            assertEquals(it, productList)
        }
        coVerify(exactly = 1) { repository.getProducts(any()) }
    }

    @Test
    fun `when getProducts and response data is null then update _errorMessage with the error`() {
        // Given
        coEvery { repository.getProducts(any()) } returns DomainItem(null, "Data is null")

        // When
        TestScope().runTest {
            viewModel.getProducts(ProductType.MEN)
        }

        // Then
        viewModel.errorMessage.observeForever {
            assertEquals(it, "Data is null")
        }
        coVerify(exactly = 1) { repository.getProducts(any()) }
    }

}