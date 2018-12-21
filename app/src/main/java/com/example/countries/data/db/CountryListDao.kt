package com.example.countries.data.db

import androidx.paging.DataSource
import androidx.room.*
import com.example.countries.model.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class CountryListDao {

    @Transaction
    open fun insertCountryList(countryList: List<Country>, needRefresh: Boolean): Completable =
        Completable.fromAction {
            if (needRefresh) deleteAll()

            countryList.forEach { country ->
                with(country) {
                    insert(countryNameAndFlag)
                    currencies.forEach { insert(it) }
                    timezones.forEach { insert(it) }
                    languages.forEach { insert(it) }
                }
            }
        }

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
    abstract fun insert(currency: Currency)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(timezone: Timezone)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(language: Language)

}