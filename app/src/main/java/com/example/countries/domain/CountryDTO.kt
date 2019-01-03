package com.example.countries.domain

import com.example.countries.data.db.entity.CountryEntity
import com.example.countries.data.db.entity.CurrencyEntity
import com.example.countries.data.db.entity.LanguageEntity
import com.example.countries.data.db.entity.TimezoneEntity

data class CountryDTO(
    val alphaCode: String,
    val name: String,
    val flagName: String,
    val languages: List<LanguageDTO>,
    val currencies: List<CurrencyDTO>,
    val timezones: List<TimezoneDTO>
) {

    fun toEntity(): CountryEntity =
            CountryEntity(alphaCode, name, flagName)

}

data class LanguageDTO(
    val iso639: String,
    val name: String,
    val nativeName: String
) {

    fun toEntity(): LanguageEntity =
        LanguageEntity(iso639, name, nativeName)

}

data class CurrencyDTO(
    val code: String,
    val name: String,
    val symbol: String
) {

    fun toEntity(): CurrencyEntity =
            CurrencyEntity(code, name, symbol)

}

data class TimezoneDTO(
    val timezone: String
) {
    
    fun toEntity(): TimezoneEntity =
        TimezoneEntity(timezone)

}