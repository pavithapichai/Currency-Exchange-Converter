package com.example.paypaycurrencyconverter.local.dao

import androidx.room.*
import com.example.paypaycurrencyconverter.local.entity.CurrencyEntity

@Dao
interface CurrencyExchangeDao {
 @Query("Select * from currencies order by id asc")
 fun getAllData() : List<CurrencyEntity>

 @Update
 fun update (currencyEntity: CurrencyEntity)

 @Insert
 fun insert(currencyEntity: CurrencyEntity)

 @Query("Select * from currencies where id = :id")
 fun getCurrency(id:String):CurrencyEntity?

 @Transaction
 fun insertOrUpdate(currencies : Map<String , Double>) {
     currencies.forEach { currencyRate ->
        val localData = getCurrency(currencyRate.key)
         if(localData == null){
            insert(CurrencyEntity(currencyRate.key,currencyRate.value))
         }else {

         }
     }

    }
}