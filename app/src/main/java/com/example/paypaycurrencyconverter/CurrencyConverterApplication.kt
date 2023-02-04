package com.example.paypaycurrencyconverter

import android.app.Application
import com.example.paypaycurrencyconverter.di.AppModule
import com.example.paypaycurrencyconverter.di.ApplicationComponent
import com.example.paypaycurrencyconverter.di.DaggerApplicationComponent


class CurrencyConverterApplication : Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}