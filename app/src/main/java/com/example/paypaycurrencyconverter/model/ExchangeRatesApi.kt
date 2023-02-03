package com.example.paypaycurrencyconverter.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ExchangeRatesApi(
    val disclaimer :String,
    val  license:String,
    val timestamp:Long,
    val base:String,
    var rates: HashMap<String, Double> = HashMap(),
    ) : Parcelable
