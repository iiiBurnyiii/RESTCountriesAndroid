package com.example.countries.util

import com.example.countries.data.api.CountryResponse
import com.example.countries.model.*

fun CountryResponse.toEntity(): Country {
    val countryNameAndFlag = CountryNameAndFlag(
        alphaCode = alpha3Code,
        name = name,
        flag = flagUrl
    )

    val currencies: List<Currency> = currencies
        //Ошибки в ответе сервера
        .filter { it.name != "[D]" && it.name != null && it.code != null }
        .map {
            Currency(code = it.code ?: "none",
                name = it.name ?: "none",
                symbol = it.symbol ?: "none",
                alphaCode = countryNameAndFlag.alphaCode)
        }

    val languages: List<Language> = languages.map {
        Language(it.iso639, it.name, it.nativeName, countryNameAndFlag.alphaCode)
    }

    val timezones: List<Timezone> = timezones.map {
        Timezone(it, countryNameAndFlag.alphaCode)
    }

    return Country(
        countryNameAndFlag = countryNameAndFlag,
        currencies = currencies,
        languages = languages,
        timezones = timezones
    )
}