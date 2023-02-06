package com.example.paypaycurrencyconverter.mainUseCase

import com.example.paypaycurrencyconverter.local.entity.CurrencyEntity
import javax.inject.Inject

interface FetchAllCurrenciesUseCase {
    suspend operator fun invoke() : List<CurrencyEntity>
}

class FetchAllCurrenciesUseCaseImpl @Inject constructor(
        private val currencyRepo: CurrencyRepo
        ): FetchAllCurrenciesUseCase {
    override suspend fun invoke() : List<CurrencyEntity>{
       return currencyRepo.getAllCurrencyData()
    }
}