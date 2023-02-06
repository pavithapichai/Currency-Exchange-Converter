package com.example.paypaycurrencyconverter.mainUseCase.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.paypaycurrencyconverter.mainUseCase.FetchCurrencyUseCase

class CurrencyWorker(
    context : Context,
    workerParameters: WorkerParameters,
    private val fetchCurrencyUseCase: FetchCurrencyUseCase
) : CoroutineWorker(context,workerParameters) {
    override suspend fun doWork(): Result {
        fetchCurrencyUseCase()
        return Result.success()
    }

}