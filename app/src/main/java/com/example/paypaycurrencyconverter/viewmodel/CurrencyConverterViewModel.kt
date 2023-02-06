package com.example.paypaycurrencyconverter.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paypaycurrencyconverter.helper.SingleLiveEvent
import com.example.paypaycurrencyconverter.local.entity.CurrencyEntity
import com.example.paypaycurrencyconverter.mainUseCase.FetchAllCurrenciesUseCase
import com.example.paypaycurrencyconverter.mainUseCase.FetchCurrencyUseCase
import kotlinx.coroutines.launch


class CurrencyConverterViewModel  (
    private val fetchAllCurrenciesUseCase: FetchAllCurrenciesUseCase,
    private val fetchCurrenyUseCase : FetchCurrencyUseCase
) : ViewModel()  {


    //cached
    private val _data = SingleLiveEvent<List<CurrencyEntity>>()
    //public
    val currencyData  =  _data

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