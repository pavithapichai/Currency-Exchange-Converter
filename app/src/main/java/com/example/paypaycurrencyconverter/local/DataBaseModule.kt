package com.example.paypaycurrencyconverter.local

import android.content.Context
import androidx.room.Room
import com.example.paypaycurrencyconverter.local.dao.CurrencyExchangeDao
import com.example.paypaycurrencyconverter.mainUseCase.*
import dagger.Module
import dagger.Provides


import javax.inject.Singleton

@Module

class DataBaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(context : Context) : AppDataBase {
        return Room.databaseBuilder(context,AppDataBase::class.java,"currency_converter").build()
    }

    @Provides
    @Singleton
    fun providesCurrencyExchangeDao(appDataBase: AppDataBase) :CurrencyExchangeDao{
        return appDataBase.getCurrencyExchangeDao()
    }



}