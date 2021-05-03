package com.app.adidas.utils

import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> singleSchedulers(): SingleTransformer<T, T> {
    return SingleTransformer { single ->
        return@SingleTransformer single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}