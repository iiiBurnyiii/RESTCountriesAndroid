package com.example.countries.data.db.dao

import androidx.paging.DataSource
import androidx.room.*
import com.example.countries.model.Country
import io.reactivex.Single

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(country: Country)

    @Update
    fun update(country: Country)

    @Query("SELECT * FROM countries")
    fun getCountries(): DataSource.Factory<Int, Country>

    @Query("SELECT * FROM countries WHERE alpha_code = :alphaCode")
    fun getCountryTitleByAlphaCode(alphaCode: String): Single<Country>

}