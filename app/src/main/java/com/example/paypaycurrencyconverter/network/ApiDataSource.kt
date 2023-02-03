package com.example.paypaycurrencyconverter.network

import javax.inject.Inject

class ApiDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getConvertedRate(access_key: String, from: String, to: String, amount: Double) =
        apiService.convertCurrency()

    suspend fun getExchangeRate(access_key: String,  amount: Double) =
        apiService.getExchangeRate(access_key)

}