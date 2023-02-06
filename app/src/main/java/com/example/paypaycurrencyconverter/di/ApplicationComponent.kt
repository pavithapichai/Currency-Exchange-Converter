package com.example.paypaycurrencyconverter.di


import com.example.paypaycurrencyconverter.local.AppDataBase
import com.example.paypaycurrencyconverter.local.DataBaseModule
import com.example.paypaycurrencyconverter.mainUseCase.CurrencyModule
import com.example.paypaycurrencyconverter.mainUseCase.workmanager.CurrencyWorkerFactory
import com.example.paypaycurrencyconverter.view.CurrencyConverterActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataBaseModule::class,
        ViewModelModule::class,
        CurrencyModule::class
    ]
)
interface ApplicationComponent {
    fun appDatabase(): AppDataBase
    fun getCurrencyWorkerFactory(): CurrencyWorkerFactory
    fun inject(activity: CurrencyConverterActivity)
}