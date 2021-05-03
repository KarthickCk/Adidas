package com.app.adidas.data

import com.app.adidas.domain.Product
import io.reactivex.Single

interface IProductRepository {
    fun getProducts(): Single<List<Product>>
}