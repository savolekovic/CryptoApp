package com.vosaa.cryptoapp

import android.app.Application
import com.vosaa.cryptoapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CryptoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoApp)
            androidLogger()

            modules(appModule)
        }
    }
}