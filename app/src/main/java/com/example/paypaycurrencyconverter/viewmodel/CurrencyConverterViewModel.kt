package com.example.paypaycurrencyconverter.view

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paypaycurrencyconverter.helper.Resource
import com.example.paypaycurrencyconverter.helper.SingleLiveEvent
import com.example.paypaycurrencyconverter.model.ApiResponse
import com.example.paypaycurrencyconverter.model.ExchangeRatesApi
import com.example.paypaycurrencyconverter.model.Rates
import com.example.paypaycurrencyconverter.viewmodel.CurrencyRepo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CurrencyConverterViewModel @ViewModelInject constructor(private val mainRepo: CurrencyRepo) : ViewModel()  {


    //cached
    private val _data = SingleLiveEvent<Resource<ApiResponse>>()
    private val _edata = SingleLiveEvent<Resource<ExchangeRatesApi>>()


    //public
    val data  =  _data
    val exchangeData  =  _edata
    val convertedRate = MutableLiveData<Double>()


    //Public function to get the result of conversion
    fun getConvertedData(access_key: String, from: String, to: String, amount: Double) {
        viewModelScope.launch {
            mainRepo.getConvertedData(access_key, from, to, amount).collect {
                data.value = it
            }
        }
    }

    //Public function to get the result of Exchange Rate
    fun getExchangeData(access_key: String ,amount: Double) {
        viewModelScope.launch {
            mainRepo.getExchangeData(access_key, amount).collect {
                exchangeData.value = it
                Log.e("Data updated", it.data.toString())
            }
        }
    }


}