package com.example.paypaycurrencyconverter.mainUseCase.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.paypaycurrencyconverter.mainUseCase.FetchCurrencyUseCase

class CurrencyWorkerFactory(
    private val fetchCurrencyUseCase: FetchCurrencyUseCase
):WorkerFactory (){
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when(workerClassName == CurrencyWorker::class.java.name){
            true -> CurrencyWorker (appContext,workerParameters,fetchCurrencyUseCase)
            else -> null
        }
    }
}