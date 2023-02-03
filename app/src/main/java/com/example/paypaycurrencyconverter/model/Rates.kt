package com.example.paypaycurrencyconverter.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rates(
    val currency_name: String,
    val rate: Double
) : Parcelable