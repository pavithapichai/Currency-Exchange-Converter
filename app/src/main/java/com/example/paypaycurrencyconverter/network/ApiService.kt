package com.example.paypaycurrencyconverter.network

import com.example.paypaycurrencyconverter.model.ExchangeRatesApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("latest.json")
    suspend fun getExchangeRate(
        @Query("app_id") app_id: String,
    ) : Response<ExchangeRatesApi>
}