package com.example.countries.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.countries.model.Country
import io.reactivex.Single

@Dao
abstract class CountriesDao {

    @Transaction
    @Query("SELECT * FROM countries")
    abstract fun getAllCountries(): Single<List<Country>>

    @Transaction
    @Query("SELECT * FROM countries WHERE alpha_code = :countryAlphaCode")
    abstract fun getCountry(countryAlphaCode: String): Single<Country>

    @Insert
    abstract fun insert()

}