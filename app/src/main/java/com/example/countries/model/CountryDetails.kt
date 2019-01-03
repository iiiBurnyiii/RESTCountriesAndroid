package com.example.countries.model

data class CountryDetails(
    val alphaCodeWithName: String,
    val flagName: String,
    val languages: List<CommonModel>,
    val currencies: List<CommonModel>,
    val timezones: List<CommonModel>
)
