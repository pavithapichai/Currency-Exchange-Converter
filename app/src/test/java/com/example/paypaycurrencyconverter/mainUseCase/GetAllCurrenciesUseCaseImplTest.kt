package com.example.paypaycurrencyconverter.mainUseCase


import com.example.paypaycurrencyconverter.local.entity.CurrencyEntity
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetAllCurrenciesUseCaseImplTest {

    @Mock
    private lateinit var currencyRepository: CurrencyRepo

    private lateinit var useCase: FetchAllCurrenciesUseCaseImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        useCase = FetchAllCurrenciesUseCaseImpl(currencyRepository)
    }

    @Test
    fun `invoke, should return match values`() = runTest {
        val currencies = listOf(
            CurrencyEntity("USD", 1.0),
            CurrencyEntity("IDR", 15000.0)
        )
        whenever(currencyRepository.getAllCurrencyData()).thenReturn(currencies)
        useCase.invoke().let { result ->
            assertEquals(2, result.size)
            assertEquals("USD", result[0].id)
            assertEquals(1.0, result[0].baseRate, 0.0)
            assertEquals("IDR", result[1].id)
            assertEquals(15000.0, result[1].baseRate, 0.0)
        }
        verify(currencyRepository).getAllCurrencyData()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        verifyNoMoreInteractions(currencyRepository)
    }
}