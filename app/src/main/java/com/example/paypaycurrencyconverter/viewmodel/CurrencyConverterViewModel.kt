package com.example.paypaycurrencyconverter.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paypaycurrencyconverter.helper.Resource
import com.example.paypaycurrencyconverter.helper.SingleLiveEvent
import com.example.paypaycurrencyconverter.local.entity.CurrencyEntity
import com.example.paypaycurrencyconverter.model.ApiResponse
import com.example.paypaycurrencyconverter.model.ExchangeRatesApi
import com.example.paypaycurrencyconverter.mainUseCase.CurrencyRepo
import com.example.paypaycurrencyconverter.mainUseCase.FetchAllCurrenciesUseCase
import com.example.paypaycurrencyconverter.mainUseCase.FetchCurrencyUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject


class CurrencyConverterViewModel  (
    private val fetchAllCurrenciesUseCase: FetchAllCurrenciesUseCase,
    private val fetchCurrenyUseCase : FetchCurrencyUseCase
) : ViewModel()  {


    //cached
    private val _data = SingleLiveEvent<List<CurrencyEntity>>()
    private val _edata = SingleLiveEvent<Resource<ExchangeRatesApi>>()


    //public
    val currencyData  =  _data
//    val exchangeData  =  _edata
//    val convertedRate = MutableLiveData<Double>()


//    //Public function to get the result of conversion
//    fun getConvertedData(access_key: String, from: String, to: String, amount: Double) {
//        viewModelScope.launch {
//            mainRepo.getConvertedData(access_key, from, to, amount).collect {
//                data.value = it
//            }
//        }
//    }
//
//    //Public function to get the result of Exchange Rate
//    fun getExchangeData(access_key: String ,amount: Double) {
//        viewModelScope.launch {
//            mainRepo.getExchangeData(access_key, amount).collect {
//                exchangeData.value = it
//                Log.e("Data updated", it.data.toString())
//            }
//        }
//    }

    fun getAllData() {
        viewModelScope.launch {
            val localCurrencies = fetchAllCurrenciesUseCase()
            if (localCurrencies.isEmpty()) {
                fetchCurrenyUseCase()
                val nCurrencies = fetchAllCurrenciesUseCase()
                currencyData.postValue(nCurrencies)
            } else {
                currencyData.postValue(localCurrencies)
            }
        }
    }


}