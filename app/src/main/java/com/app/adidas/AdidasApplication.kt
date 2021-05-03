package com.app.adidas

import android.app.Application
import com.app.adidas.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AdidasApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AdidasApplication)
            modules(
                koinModules
            )
        }
    }
}