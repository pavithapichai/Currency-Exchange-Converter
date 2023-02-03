package com.example.paypaycurrencyconverter.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.example.paypaycurrencyconverter.mainUseCase.FetchAllCurrenciesUseCase

class CurrencyConverterViewModelFactory(
    private val FetchAllCurrency: FetchAllCurrenciesUseCase
) : ViewModelProvider.Factory {
}