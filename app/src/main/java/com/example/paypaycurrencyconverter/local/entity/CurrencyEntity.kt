package com.example.paypaycurrencyconverter.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity ( tableName = "currencies")
class CurrencyEntity (
        @PrimaryKey
        @ColumnInfo (name = "id")
        val id:String,

        @ColumnInfo(name ="base_rate")
        val baseRate : Double
)