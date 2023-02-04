package com.example.paypaycurrencyconverter.di

import com.example.paypaycurrencyconverter.di.AppModule
import com.example.paypaycurrencyconverter.local.AppDataBase
import com.example.paypaycurrencyconverter.local.DataBaseModule
import com.example.paypaycurrencyconverter.view.CurrencyConverterActivity
import com.example.paypaycurrencyconverter.mainUseCase.CurrencyModule


import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataBaseModule::class,
       // NetworkModule::class,
        ViewModelModule::class,

        CurrencyModule::class
    ]
)
interface ApplicationComponent {
    fun appDatabase(): AppDataBase
   // fun getFetchCurrencyWorkerFactory(): FetchCurrencyWorkerFactory

    fun inject(activity: CurrencyConverterActivity)
}