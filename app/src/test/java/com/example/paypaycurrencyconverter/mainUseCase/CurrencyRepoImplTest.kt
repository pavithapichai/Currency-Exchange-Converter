package com.example.paypaycurrencyconverter.mainUseCase


import com.example.paypaycurrencyconverter.local.dao.CurrencyExchangeDao
import com.example.paypaycurrencyconverter.local.entity.CurrencyEntity
import com.example.paypaycurrencyconverter.model.ExchangeRatesApi
import com.example.paypaycurrencyconverter.network.ApiDataSource
import com.example.paypaycurrencyconverter.network.ApiService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurrencyRepoImplTest {

    @Mock
    private lateinit var currencyDao: CurrencyExchangeDao
    @Mock
    private lateinit var currencyService: ApiDataSource
    private lateinit var currencyRepository: CurrencyRepo

    @Mock
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        currencyRepository = CurrencyRepo(currencyDao, currencyService, UnconfinedTestDispatcher())
    }

    @Test
    fun `fetchCurrency, should return match values`() = runTest {

        val currenciesResponse = ExchangeRatesApi("","",0L,"",
            hashMapOf("USD" to 1.0)
        )
        whenever(currencyService.getExchangeRate()).thenReturn(Response.success(currenciesResponse))
       whenever(apiService.getExchangeRate(any())).thenReturn(Response.success(currenciesResponse))
        val result = currencyRepository.getDataFromApi()
        verify(currencyService).getExchangeRate()

        assertTrue(result.isSuccessful)
    }

    @Test
    fun `getAllData, should return match values`() = runTest {
        val currencies = listOf(CurrencyEntity("USD", 1.0))
        whenever(currencyDao.getAllData()).thenReturn(currencies)
        val result = currencyRepository.getAllCurrencyData()
        verify(currencyDao).getAllData()
        assertEquals("USD", result[0].id)
        assertEquals(1.0, result[0].baseRate, 0.0)
    }

    @Test
    fun `insertOrUpdateCurrencies, should invoke insertOrUpdate in dao`() = runTest {
        val currencies = mapOf("USD" to 1.0)
        currencyRepository.insertOrUpdateCurrencies(currencies)
        verify(currencyDao).insertOrUpdate(currencies)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(
            currencyDao,
            currencyService
        )
    }
}