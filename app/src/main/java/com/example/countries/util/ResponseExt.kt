package com.example.countries.util

import com.example.countries.data.api.CountryResponse
import com.example.countries.model.CountryDetails
import com.example.countries.model.Currency
import com.example.countries.model.Language
import com.example.countries.model.Timezone

fun List<CountryResponse>.toCountryDetailsList(): List<CountryDetails> {
    val list: MutableList<CountryDetails> = mutableListOf()

    this.forEach { countryResponse ->
        val currencies: List<Currency> =countryResponse. currencies
            //Ошибки в ответе сервера
            .filter { it.name != "[D]" && it.name != null && it.code != null }
            .map {
                Currency(code = it.code ?: "none",
                    name = it.name ?: "none",
                    symbol = it.symbol ?: "none")
            }

        val languages: List<Language> = countryResponse.languages.map {
            Language(it.iso639, it.name, it.nativeName)
        }

        val timezones: List<Timezone> = countryResponse.timezones.map {
            Timezone(it)
        }

        val model = CountryDetails(
            alphaCode = countryResponse.alpha3Code,
            name = countryResponse.name,
            flag = countryResponse.flagUrl,
            currencies = currencies,
            languages = languages,
            timezones = timezones
        )

        list.add(model)
    }

    return list.toList()
}