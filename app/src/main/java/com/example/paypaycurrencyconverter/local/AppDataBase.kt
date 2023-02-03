package com.example.paypaycurrencyconverter.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paypaycurrencyconverter.local.dao.CurrencyExchangeDao
import com.example.paypaycurrencyconverter.local.entity.CurrencyEntity

@Database(entities = [CurrencyEntity::class],version =1 ,exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract  fun getCurrencyExchangeDao() :CurrencyExchangeDao
}