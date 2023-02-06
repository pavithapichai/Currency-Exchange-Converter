package com.example.paypaycurrencyconverter.mainUseCase

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.impl.utils.SerialExecutor
import androidx.work.impl.utils.taskexecutor.TaskExecutor
import com.example.paypaycurrencyconverter.mainUseCase.workmanager.CurrencyWorker
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurrencyWorkerTest {

    @Mock
    private lateinit var context: Context
    @Mock
    private lateinit var workerParameters: WorkerParameters
    @Mock
    private lateinit var fetchCurrenciesUseCase: FetchCurrencyUseCase

    private lateinit var fetchCurrencyWorker: CurrencyWorker

    @Before
    fun setUp() {
        val executor = mock<TaskExecutor>()
        whenever(workerParameters.taskExecutor).thenReturn(executor)

        val serialExecutor = SerialExecutor(Executors.newSingleThreadExecutor())
        whenever(executor.backgroundExecutor).thenReturn(serialExecutor)

        fetchCurrencyWorker = CurrencyWorker(
            context,
            workerParameters,
            fetchCurrenciesUseCase
        )
    }

    @Test
    fun `doWork, should fetch currencies`() = runTest {
        val result = fetchCurrencyWorker.doWork()
        verify(fetchCurrenciesUseCase).invoke()
        verify(workerParameters).taskExecutor
        assertEquals(ListenableWorker.Result.success(), result)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(
            context,
            workerParameters,
            fetchCurrenciesUseCase
        )
    }
}