package com.example.paypaycurrencyconverter.mainUseCase

import android.util.Log
import com.example.paypaycurrencyconverter.helper.ApiEndpoints
import com.example.paypaycurrencyconverter.viewmodel.CurrencyRepo
import java.io.IOException

interface FetchAllCurrenciesUseCase {
    suspend operator fun invoke()
}

class FetchAllCurrenciesUseCaseImpl (
        private val currencyRepo: CurrencyRepo
        ): FetchAllCurrenciesUseCase {
    override suspend fun invoke() {

        try {
            val response = currencyRepo.getExchangeData(ApiEndpoints.API_KEY,0.0)
             response.collect {
                if (it.data. && responseBody != null) {
                    currencyRepository.insertOrUpdateCurrencies(responseBody.rates)
                }
            }

        } catch (ioException: IOException) {
            Log.d(this.javaClass.simpleName, "Got an exception: $ioException")
        }
    }
}