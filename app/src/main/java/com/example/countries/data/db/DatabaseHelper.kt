package com.example.countries.data.db

import android.content.Context
import androidx.paging.DataSource
import androidx.room.Transaction
import com.example.countries.model.Country
import com.example.countries.model.CountryDetails
import io.reactivex.Single
import io.reactivex.functions.Action
import io.reactivex.rxkotlin.Singles
import io.reactivex.rxkotlin.toCompletable


class DatabaseHelper(context: Context) {

    val db = CountriesDatabase.getInstance(context)

    @Transaction
    fun insert(countries: List<CountryDetails>?) = Action {
        countries?.forEach { country ->
            db.countryDao().insert(
                Country(country.alphaCode, country.name, country.flag))
            db.languageDao().insert(country.languages)
            db.currencyDao().insert(country.currencies)
            db.timezoneDao().insert(country.timezones)

            insertJoin(country)
        }
    }.toCompletable()

    @Transaction
    fun update(countries: List<CountryDetails>?) = Action {
        countries?.forEach { country ->
            db.countryDao().update(
                Country(country.alphaCode, country.name, country.flag))
            db.languageDao().update(country.languages)
            db.currencyDao().update(country.currencies)
            db.timezoneDao().update(country.timezones)
        }
    }.toCompletable()

    @Transaction
    fun getCountry(alphaCode: String): Single<CountryDetails> =
        Singles.zip(
            db.countryDao().getCountryTitleByAlphaCode(alphaCode),
            db.languageDao().getLanguagesByAlphaCode(alphaCode),
            db.currencyDao().getCurrenciesByAlphaCode(alphaCode),
            db.timezoneDao().getTimezonesByAlphaCode(alphaCode))
        { country, languages, currencies, timezones->
            CountryDetails(country.alphaCode, country.name, country.flag,
                languages, currencies, timezones) }

    fun getCountries(): DataSource.Factory<Int, Country> =
            db.countryDao().getCountries()

    private fun insertJoin(country: CountryDetails) {
        val languages = country.languages
        val currencies = country.currencies
        val timezones = country.timezones

        val times = maxOf<Int>(languages.size,
            currencies.size, timezones.size) - 1

        for (i in 0..times) {
            db.joinDao().insert(
                JoinEntity(
                    countryAlphaCode = country.alphaCode,
                    languageCode = languages.elementAtOrNull(i)?.code,
                    currencyCode = currencies.elementAtOrNull(i)?.code,
                    timezoneCode = timezones.elementAtOrNull(i)?.timezone
                )
            )
        }
    }

}