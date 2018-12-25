package com.example.countries.data.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.countries.data.db.entity.CountryTitle
import io.reactivex.Single

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(country: CountryTitle)

    @Query("SELECT * FROM countries")
    fun getCountries(): DataSource.Factory<Int, CountryTitle>

    @Query("SELECT * FROM countries WHERE alpha_code = :alphaCode")
    fun getCountryTitleByAlphaCode(alphaCode: String): Single<CountryTitle>

}