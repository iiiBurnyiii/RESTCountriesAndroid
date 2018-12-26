package com.example.countries.data.db.dao

import androidx.room.*
import com.example.countries.model.Currency
import io.reactivex.Single

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(currencies: List<Currency>)

    @Update
    fun update(correncies: List<Currency>)

    @Query("""
        SELECT code, name, symbol
        FROM currencies JOIN join_entity
        ON currencies.code = join_entity.currency_code
        WHERE join_entity.country_alpha_code = :alphaCode
    """)
    fun getCurrenciesByAlphaCode(alphaCode: String): Single<List<Currency>>
}