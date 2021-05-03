package com.app.adidas.ui

import androidx.lifecycle.*
import com.app.adidas.data.IProductRepository
import com.app.adidas.domain.Product

class ProductsViewModel(
    private val iProductRepository: IProductRepository
) : ViewModel(), LifecycleObserver {

    val uiState = MutableLiveData<UIState>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getProducts() {
        iProductRepository.getProducts()
            .subscribe({
                uiState.value = UIState.Products(it)
            }, {
                uiState.value = UIState.Error(it.message!!)
            })
    }

    sealed class UIState {
        class Products(val list: List<Product>): UIState()
        class Error(val message: String): UIState()
    }
}