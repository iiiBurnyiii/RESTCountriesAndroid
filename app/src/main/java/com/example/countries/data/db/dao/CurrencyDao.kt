package com.example.countries.data.db.dao

import androidx.room.*
import com.example.countries.data.db.entity.CurrencyEntity
import io.reactivex.Single

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(currencies: List<CurrencyEntity>)

    @Update
    fun update(currencies: List<CurrencyEntity>)

    @Query("""
        SELECT code, name, symbol
        FROM currencies JOIN join_entity
        ON currencies.code = join_entity.currency_code
        WHERE join_entity.country_alpha_code = :alphaCode
    """)
    fun getCurrenciesByAlphaCode(alphaCode: String): Single<List<CurrencyEntity>>
}