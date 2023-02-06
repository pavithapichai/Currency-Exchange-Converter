package com.example.paypaycurrencyconverter.mainUseCase

import android.util.Log
import com.example.paypaycurrencyconverter.network.BaseDataSource
import kotlinx.coroutines.flow.flow

import okio.IOException
import retrofit2.Response
import javax.inject.Inject

interface FetchCurrencyUseCase {
    suspend operator fun invoke()
}

class FetchCurrencyUseCaseImpl @Inject constructor(private val currencyRepo:CurrencyRepo) : FetchCurrencyUseCase,BaseDataSource(){
    override suspend fun invoke() {
        try {
            val response = currencyRepo.getDataFromApi()
            val responseBody = response.body()
                if (response.isSuccessful &&  responseBody != null) {
                    currencyRepo.insertOrUpdateCurrencies(responseBody.rates)
                }


        } catch (ioException: java.io.IOException) {
            Log.d(this.javaClass.simpleName, "Got an exception: $ioException")
        }

    }

}