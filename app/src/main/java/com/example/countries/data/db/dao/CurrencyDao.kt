package com.example.countries.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.countries.data.db.entity.Currency
import io.reactivex.Single

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCurrency(currency: Currency)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(currencies: List<Currency>)

    @Query("""
        SELECT code, name, symbol
        FROM currencies JOIN join_entity
        ON currencies.code = join_entity.currency_code
        WHERE join_entity.country_alpha_code = :alphaCode
    """)
    fun getCurrenciesByAlphaCode(alphaCode: String): Single<List<Currency>>
}