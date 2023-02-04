package com.example.paypaycurrencyconverter.di

import com.example.paypaycurrencyconverter.mainUseCase.FetchAllCurrenciesUseCase
import com.example.paypaycurrencyconverter.mainUseCase.FetchCurrencyUseCase
import com.example.paypaycurrencyconverter.viewmodel.CurrencyConverterViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun providesCurrencyViewModelFactory(
        fetchCurrenciesUseCase: FetchCurrencyUseCase,
        getAllCurrenciesUseCase: FetchAllCurrenciesUseCase
    ) : CurrencyConverterViewModelFactory {
        return CurrencyConverterViewModelFactory(
            getAllCurrenciesUseCase  ,
            fetchCurrenciesUseCase
        )
    }
}
