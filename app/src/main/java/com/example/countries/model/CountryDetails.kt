package com.example.countries.model

data class CountryDetails(
    val alphaCode: String,
    val name: String,
    val flag: String,
    val languages: List<Language>,
    val currencies: List<Currency>,
    val timezones: List<Timezone>
) {
    val alphaCodeWithName = "$alphaCode, $name"
}
