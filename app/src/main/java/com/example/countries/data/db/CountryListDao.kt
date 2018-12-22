package com.example.countries.data.db

import androidx.paging.DataSource
import androidx.room.*
import com.example.countries.model.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class CountryListDao {

    @Transaction
    @Query("SELECT * FROM countries")
    abstract fun getAllCountries(): DataSource.Factory<Int, Country>

    @Transaction
    @Query("DELETE FROM countries")
    abstract fun deleteAll()

    @Transaction
    @Query("SELECT * FROM countries WHERE alpha_code = :countryAlphaCode")
    abstract fun getCountry(countryAlphaCode: String): Single<Country>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(country: CountryNameAndFlag)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertCurrencies(currencies: List<Currency>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertTimezones(timezones: List<Timezone>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertLanguages(languages: List<Language>)

}