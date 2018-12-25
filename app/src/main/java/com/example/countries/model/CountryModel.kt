package com.example.countries.model

import com.example.countries.data.db.entity.Currency
import com.example.countries.data.db.entity.Language
import com.example.countries.data.db.entity.Timezone

data class CountryModel(
    val alphaCode: String,
    val name: String,
    val flag: String,
    val languages: List<Language>,
    val currencies: List<Currency>,
    val timezones: List<Timezone>
) {
    val alphaCodeWithName = "$alphaCode, $name"
}
