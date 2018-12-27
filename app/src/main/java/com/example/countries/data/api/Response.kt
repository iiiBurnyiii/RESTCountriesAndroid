package com.example.countries.data.api

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    val alpha3Code: String,
    val name: String,
    @SerializedName("flag")
    val flagUrl: String,
    val timezones: List<String>,
    val currencies: List<CurrencyResponse>,
    val languages: List<LanguageResponse>
)

data class LanguageResponse(
    @SerializedName("iso639_2")
    val iso639: String,
    val name: String,
    val nativeName: String
)

data class CurrencyResponse(
    val code: String?,
    val name: String?,
    val symbol: String?
)
