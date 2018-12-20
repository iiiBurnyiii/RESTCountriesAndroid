package com.example.countries.model

data class Country(
    val name: String,
    val flag: String,
    val languages: Set<Language>,
    val timezones: Set<Timezone>,
    val currencies: Set<Currency>
)