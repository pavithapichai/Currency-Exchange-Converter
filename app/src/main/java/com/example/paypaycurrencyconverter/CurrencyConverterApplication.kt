package com.example.paypaycurrencyconverter

import android.app.Application
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkManager
import com.example.paypaycurrencyconverter.di.AppModule
import com.example.paypaycurrencyconverter.di.ApplicationComponent
import com.example.paypaycurrencyconverter.di.DaggerApplicationComponent
import com.example.paypaycurrencyconverter.mainUseCase.workmanager.SetupCurrency


class CurrencyConverterApplication : Application() , Configuration.Provider{

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        SetupCurrency(WorkManager.getInstance(this)).start()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        val delegatingWorkerFactory = DelegatingWorkerFactory()
        delegatingWorkerFactory.addFactory(applicationComponent.getCurrencyWorkerFactory())
        return Configuration.Builder().setWorkerFactory(delegatingWorkerFactory).build()

    }
}