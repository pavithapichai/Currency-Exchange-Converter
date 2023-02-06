package com.example.paypaycurrencyconverter.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ApiResponse(
    val value: String,
    val base_currency_code: String,
    val base_currency_name: String,
    var rates: Map<String, Double> = HashMap(),
    val status: String,
    val updated_date: String
):Parcelable