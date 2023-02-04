package com.example.paypaycurrencyconverter.network

import com.example.paypaycurrencyconverter.helper.ApiEndpoints
import javax.inject.Inject

class ApiDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getConvertedRate(access_key: String, from: String, to: String, amount: Double) =
        apiService.convertCurrency()

    suspend fun getExchangeRate() =
        apiService.getExchangeRate(ApiEndpoints.API_KEY)

}