package com.app.adidas.di

import com.app.adidas.data.IProductRepository
import com.app.adidas.data.ProductApi
import com.app.adidas.data.ProductRepository
import com.app.adidas.ui.ProductsViewModel
import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

const val API_READ_TIMEOUT = 45L
const val API_CONNECT_TIMEOUT = 5L

@JvmField
val koinModules = module {

    factory<RxJava2CallAdapterFactory> {
        RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    } bind CallAdapter.Factory::class

    factory<GsonConverterFactory> {
        GsonConverterFactory.create()
    } bind Converter.Factory::class

    factory<Retrofit.Builder> {
        val builder = Retrofit.Builder()
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
        builder.baseUrl("http://10.0.2.2:3001/")
    }

    single<OkHttpClient>() {
        val client = OkHttpClient.Builder()
            .readTimeout(API_READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(API_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        client.build()
    }

    factory<Retrofit>() {
        get<Retrofit.Builder>()
            .client(get<OkHttpClient>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    factory<ProductApi> {
        get<Retrofit>().create()
    }

    factory<IProductRepository> {
        ProductRepository(get())
    }

    viewModel {
        ProductsViewModel(get())
    }
}