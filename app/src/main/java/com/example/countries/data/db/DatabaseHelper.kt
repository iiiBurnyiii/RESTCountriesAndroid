package com.example.countries.data.db

import android.content.Context
import androidx.paging.DataSource
import androidx.room.Transaction
import com.example.countries.data.db.entity.CountryTitle
import com.example.countries.data.db.entity.JoinEntity
import com.example.countries.model.CountryModel
import io.reactivex.Single
import io.reactivex.functions.Action
import io.reactivex.rxkotlin.Singles
import io.reactivex.rxkotlin.toCompletable


class DatabaseHelper(context: Context) {

    val db = CountriesDatabase.getInstance(context)

    @Transaction
    fun insert(countries: List<CountryModel>?) = Action {
        countries?.forEach { country ->
            val countryTitle =
                CountryTitle(country.alphaCode, country.name, country.flag)

            db.countryDao().insert(countryTitle)
            db.languageDao().insert(country.languages)
            db.currencyDao().insert(country.currencies)
            db.timezoneDao().insert(country.timezones)

            actionWithJoin(country) { joinEntity ->
                db.joinDao().insert(joinEntity)
            }
        }
    }.toCompletable()

    @Transaction
    fun update(countries: List<CountryModel>?) = Action {
        countries?.forEach { country ->
            actionWithJoin(country) { joinEntity ->
                db.joinDao().update(joinEntity)
            }
        }
    }.toCompletable()

    @Transaction
    fun getCountry(alphaCode: String): Single<CountryModel> =
        Singles.zip(
            db.countryDao().getCountryTitleByAlphaCode(alphaCode),
            db.languageDao().getLanguagesByAlphaCode(alphaCode),
            db.currencyDao().getCurrenciesByAlphaCode(alphaCode),
            db.timezoneDao().getTimezonesByAlphaCode(alphaCode))
        { country, languages, currencies, timezones->
            CountryModel(country.alphaCode, country.name, country.flag,
                languages, currencies, timezones) }

    fun getCountries(): DataSource.Factory<Int, CountryTitle> =
            db.countryDao().getCountries()

    private inline fun actionWithJoin(country: CountryModel, action: (JoinEntity) -> Unit) {
        val languages = country.languages
        val currencies = country.currencies
        val timezones = country.timezones

        val times = maxOf<Int>(languages.size,
            currencies.size, timezones.size)

        for (i in 0..times) {
            action(
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