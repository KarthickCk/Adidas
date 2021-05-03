package com.app.adidas.data

import com.app.adidas.domain.Product
import io.reactivex.Single
import retrofit2.http.GET

interface ProductApi {
    @GET("/product")
    fun getProducts(): Single<List<Product>>
}