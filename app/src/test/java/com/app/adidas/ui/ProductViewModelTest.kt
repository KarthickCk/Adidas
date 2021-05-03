package com.app.adidas.ui

import com.app.adidas.BaseTest
import com.app.adidas.data.IProductRepository
import com.app.adidas.data.ProductApi
import com.app.adidas.domain.Product
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class ProductViewModelTest : BaseTest() {

    @Mock
    lateinit var iProductRepository: IProductRepository

    private lateinit var productsViewModel: ProductsViewModel

    @Before
    fun setUp() {
        productsViewModel = ProductsViewModel(iProductRepository)
    }

    @Test
    fun testGetProducts() {
        val list = listOf<Product>()
        Mockito.doReturn(Single.just(list))
            .`when`(iProductRepository)
            .getProducts()

        productsViewModel.getProducts()

        Assert.assertNotNull(productsViewModel.uiState.value)
    }
}