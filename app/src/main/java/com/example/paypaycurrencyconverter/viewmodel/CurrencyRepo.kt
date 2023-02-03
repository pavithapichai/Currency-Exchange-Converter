package com.example.paypaycurrencyconverter.viewmodel
import com.example.paypaycurrencyconverter.helper.Resource
import com.example.paypaycurrencyconverter.model.ApiResponse
import com.example.paypaycurrencyconverter.network.ApiDataSource
import com.example.paypaycurrencyconverter.network.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.example.paypaycurrencyconverter.model.ExchangeRatesApi

class CurrencyRepo @Inject constructor(private val apiDataSource:ApiDataSource):BaseDataSource(){
    //Using coroutines flow to get the response from
    suspend fun getConvertedData(access_key: String, from: String, to: String, amount: Double): Flow<Resource<ApiResponse>> {
        return flow {
            emit(safeApiCall { apiDataSource.getConvertedRate(access_key, from, to, amount) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getExchangeData(access_key: String,amount: Double): Flow<Resource<ExchangeRatesApi>> {
        return flow {
            emit(safeApiCall { apiDataSource.getExchangeRate(access_key, amount) })
        }.flowOn(Dispatchers.IO)
    }
}