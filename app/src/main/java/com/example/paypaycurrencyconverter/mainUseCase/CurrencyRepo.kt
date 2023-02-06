package com.example.paypaycurrencyconverter.mainUseCase
import com.example.paypaycurrencyconverter.helper.Resource
import com.example.paypaycurrencyconverter.local.dao.CurrencyExchangeDao
import com.example.paypaycurrencyconverter.local.entity.CurrencyEntity
import com.example.paypaycurrencyconverter.network.ApiDataSource
import com.example.paypaycurrencyconverter.network.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.example.paypaycurrencyconverter.model.ExchangeRatesApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

interface ICurrencyRepository {
    suspend fun getExchangeCurrencyData(): Flow<Resource<ExchangeRatesApi>>
    suspend fun insertOrUpdateCurrencies(currencies: Map<String, Double>)
    suspend fun getAllCurrencyData(): List<CurrencyEntity>
    suspend fun getDataFromApi() :Response<ExchangeRatesApi>
}
class CurrencyRepo @Inject constructor(
    private val currencyDao: CurrencyExchangeDao,
    private val apiDataSource:ApiDataSource,
    private val dispatcher: CoroutineDispatcher
    ):BaseDataSource() , ICurrencyRepository{


    override suspend fun getExchangeCurrencyData(): Flow<Resource<ExchangeRatesApi>> {
        return flow {
            emit(safeApiCall { apiDataSource.getExchangeRate() })
        }.flowOn(Dispatchers.IO)
    }
    override suspend fun getDataFromApi () : Response<ExchangeRatesApi>{
        return withContext(dispatcher){
            apiDataSource.getExchangeRate()
        }

    }

    override suspend fun getAllCurrencyData () : List<CurrencyEntity>{
        return withContext(dispatcher){
              currencyDao.getAllData()
        }

    }

    override suspend fun insertOrUpdateCurrencies(currencies: Map<String, Double>) {
        withContext(dispatcher) {
            currencyDao.insertOrUpdate(currencies)
        }
    }



}