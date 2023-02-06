package com.example.paypaycurrencyconverter.network

import com.example.paypaycurrencyconverter.BuildConfig
import com.example.paypaycurrencyconverter.helper.ApiEndpoints
import javax.inject.Inject

class ApiDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getExchangeRate() =
        apiService.getExchangeRate(BuildConfig.APP_ID)

}