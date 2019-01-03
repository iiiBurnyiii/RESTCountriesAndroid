package com.example.countries.domain

import androidx.paging.DataSource
import androidx.room.Transaction
import com.example.countries.data.db.CountriesDatabase
import com.example.countries.data.db.entity.JoinEntity
import com.example.countries.model.CountryDetails
import com.example.countries.model.CountryTitle
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import io.reactivex.schedulers.Schedulers


class DatabaseHelper(
    private val db: CountriesDatabase
) {

    fun insert(data: List<CountryDTO>) {
        insertData(data)
    }

    fun refresh(data: List<CountryDTO>) {
        delete()
        insertData(data)
    }

    @Transaction
    fun getCountryDetails(alphaCode: String): Single<CountryDetails> =
        Singles.zip(
            db.countryDao().getCountryByAlphaCode(alphaCode),
            db.languageDao().getLanguagesByAlphaCode(alphaCode),
            db.currencyDao().getCurrenciesByAlphaCode(alphaCode),
            db.timezoneDao().getTimezonesByAlphaCode(alphaCode))
        { country, languages, currencies, timezones->
            CountryDetails("${country.alphaCode}, ${country.name}", country.flagName,
                languages.map { it.toCommonModel() },
                currencies.map { it.toCommonModel() },
                timezones.map { it.toCommonModel() })
        }.subscribeOn(Schedulers.io())

    fun getCountries(): DataSource.Factory<Int, CountryTitle> =
            db.countryDao().getCountries().map {
                CountryTitle(it.alphaCode, it.name, it.flagName)
            }

    @Transaction
    private fun insertData(data: List<CountryDTO>) {
        data.forEach { country ->
            val languages = country.languages
            val currencies = country.currencies
            val timezones = country.timezones

            db.countryDao().insert(country.toEntity())
            db.languageDao().insert(languages.map { it.toEntity() })
            db.currencyDao().insert(currencies.map { it.toEntity() })
            db.timezoneDao().insert(timezones.map { it.toEntity() })

            val times = maxOf<Int>(languages.size,
                currencies.size, timezones.size) - 1
            for (i in 0..times) {
                db.joinDao().insert(
                    JoinEntity(
                        countryAlphaCode = country.alphaCode,
                        languageCode = languages.elementAtOrNull(i)?.iso639,
                        currencyCode = currencies.elementAtOrNull(i)?.code,
                        timezoneCode = timezones.elementAtOrNull(i)?.timezone
                    )
                )
            }
        }
    }

    @Transaction
    private fun delete() {
        db.countryDao().delete()
        db.languageDao().delete()
        db.currencyDao().delete()
        db.timezoneDao().delete()
        db.joinDao().delete()
    }

}