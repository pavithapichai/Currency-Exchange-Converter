package com.example.paypaycurrencyconverter.mainUseCase

import com.example.paypaycurrencyconverter.local.dao.CurrencyExchangeDao

import com.example.paypaycurrencyconverter.network.ApiDataSource

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module

class CurrencyModule {


    @Provides
    fun providesCurrencyRepository(
        currencyDao: CurrencyExchangeDao,
        apiDataSource: ApiDataSource
    ): CurrencyRepo {
        return CurrencyRepo(
            currencyDao,
            apiDataSource,
            Dispatchers.IO
        )
    }

    @Provides
    fun providesFetchCurrenciesUseCase(
        currencyRepository: CurrencyRepo
    ): FetchCurrencyUseCase {
        return FetchCurrencyUseCaseImpl(currencyRepository)
    }

    @Provides
    fun providesGetAllCurrenciesUseCase(
        currencyRepository: CurrencyRepo
    ): FetchAllCurrenciesUseCase {
        return FetchAllCurrenciesUseCaseImpl(currencyRepository)
    }


}