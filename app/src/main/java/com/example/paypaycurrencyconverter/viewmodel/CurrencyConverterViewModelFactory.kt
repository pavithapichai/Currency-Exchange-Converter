package com.example.paypaycurrencyconverter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paypaycurrencyconverter.mainUseCase.FetchAllCurrenciesUseCase
import com.example.paypaycurrencyconverter.mainUseCase.FetchCurrencyUseCase
import com.example.paypaycurrencyconverter.view.CurrencyConverterViewModel

class CurrencyConverterViewModelFactory  (
    private val fetchAllCurrencyUseCase: FetchAllCurrenciesUseCase,
    private val fetchCurrencyUseCase: FetchCurrencyUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyConverterViewModel(fetchAllCurrencyUseCase,fetchCurrencyUseCase) as T
    }
}