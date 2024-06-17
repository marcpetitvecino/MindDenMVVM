package com.marcpetit.minddenmvvm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcpetit.minddenmvvm.domain.products.Product
import com.marcpetit.minddenmvvm.domain.products.ProductType
import com.marcpetit.minddenmvvm.domain.products.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductsRepository
): ViewModel()  {

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> get() = _productList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getProducts(productType: ProductType) {
        viewModelScope.launch {
            val response = repository.getProducts(productType)
            response.data?.let {
                _productList.postValue(it)
            } ?: _errorMessage.postValue(response.error.orEmpty())
        }
    }

}