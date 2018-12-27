package com.example.countries.data.db.dao

import androidx.paging.DataSource
import androidx.room.*
import com.example.countries.data.db.entity.CountryEntity
import io.reactivex.Single

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(country: CountryEntity)

    @Update
    fun update(country: CountryEntity)

    @Query("SELECT * FROM countries")
    fun getCountries(): DataSource.Factory<Int, CountryEntity>

    @Query("SELECT * FROM countries WHERE alpha_code = :alphaCode")
    fun getCountryByAlphaCode(alphaCode: String): Single<CountryEntity>

}