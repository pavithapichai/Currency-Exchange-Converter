package com.example.paypaycurrencyconverter.mainUseCase.workmanager

import androidx.work.*
import java.util.concurrent.TimeUnit

private const val TAG_CURRENCY_WORKER = "fetch_currency_worker"

class SetupCurrency(private val workManager: WorkManager) {

    fun start() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<CurrencyWorker>(30, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            TAG_CURRENCY_WORKER,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}