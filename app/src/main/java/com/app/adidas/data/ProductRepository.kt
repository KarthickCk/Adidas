package com.app.adidas.data

import com.app.adidas.domain.Product
import com.app.adidas.utils.singleSchedulers
import io.reactivex.Single

class ProductRepository(
    private val productApi: ProductApi
) : IProductRepository {

    override fun getProducts(): Single<List<Product>> {
        return productApi.getProducts()
            .compose(singleSchedulers())
    }
}