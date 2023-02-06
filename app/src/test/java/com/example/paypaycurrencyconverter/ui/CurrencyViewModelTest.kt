package com.example.paypaycurrencyconverter.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.paypaycurrencyconverter.local.entity.CurrencyEntity
import com.example.paypaycurrencyconverter.mainUseCase.FetchAllCurrenciesUseCase
import com.example.paypaycurrencyconverter.mainUseCase.FetchCurrencyUseCase
import com.example.paypaycurrencyconverter.view.CurrencyConverterViewModel
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurrencyViewModelTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var fetchCurrenciesUseCase: FetchCurrencyUseCase
    @Mock
    private lateinit var getAllCurrenciesUseCase: FetchAllCurrenciesUseCase

    private lateinit var viewModel: CurrencyConverterViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = CurrencyConverterViewModel(
            getAllCurrenciesUseCase,
            fetchCurrenciesUseCase
        )
    }

    @Test
     fun `getAllData, when local currencies is empty, should fetch currencies`() = runTest {
        whenever(getAllCurrenciesUseCase.invoke()).thenReturn(emptyList())

        viewModel.getAllData()

        verify(getAllCurrenciesUseCase, times(2)).invoke()
        verify(fetchCurrenciesUseCase).invoke()
    }

    @Test
    fun `getAllData, when local currencies is not empty, should not fetch currencies`() = runTest {
        val currencies = listOf(CurrencyEntity("USD", 1.0))
        whenever(getAllCurrenciesUseCase.invoke()).thenReturn(currencies)

        viewModel.getAllData()

        verify(getAllCurrenciesUseCase).invoke()
        verify(fetchCurrenciesUseCase, never()).invoke()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        verifyNoMoreInteractions(
            fetchCurrenciesUseCase,
            getAllCurrenciesUseCase
        )
    }
}