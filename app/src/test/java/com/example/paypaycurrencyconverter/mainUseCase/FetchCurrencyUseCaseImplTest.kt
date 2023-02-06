package com.example.paypaycurrencyconverter.mainUseCase


import com.example.paypaycurrencyconverter.model.ApiResponse
import com.example.paypaycurrencyconverter.model.ExchangeRatesApi
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FetchCurrencyUseCaseImplTest {

    @Mock
    private lateinit var currencyRepository: CurrencyRepo

    private lateinit var useCase: FetchCurrencyUseCaseImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        useCase = FetchCurrencyUseCaseImpl(currencyRepository)
    }

    @Test
    fun `invoke, when response is not success, should not insert or update currency`() = runTest {
       // whenever(currencyRepository.getExchangeCurrencyData()).thenReturn(Response.error(500, Gson().toJson(null).toResponseBody()))

        useCase.invoke()

        verify(currencyRepository).getExchangeCurrencyData()
        verify(currencyRepository, never()).insertOrUpdateCurrencies(any())
    }

    @Test
    fun `invoke, when response body is null, should not insert or update currency`() = runTest {
      //  whenever(currencyRepository.getExchangeCurrencyData()).thenReturn(Response.success(null))

        useCase.invoke()

        verify(currencyRepository).getExchangeCurrencyData()
        verify(currencyRepository, never()).insertOrUpdateCurrencies(any())
    }

    @Test
    fun `invoke, when response is success, should insert or update currency`() = runTest {
        val currenciesResponse = ExchangeRatesApi("","",0L,"",
            mapOf("USD" to 1.0) as HashMap<String, Double>
        )
        //whenever(currencyRepository.getExchangeCurrencyData()).thenReturn(Response.success(currenciesResponse))

        useCase.invoke()

        verify(currencyRepository).getExchangeCurrencyData()
        verify(currencyRepository).insertOrUpdateCurrencies(currenciesResponse.rates)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        verifyNoMoreInteractions(currencyRepository)
    }
}


