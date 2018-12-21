package com.example.countries.model

import androidx.room.Embedded
import androidx.room.Relation

data class Country(
    @Embedded
    val countryNameAndFlag: CountryNameAndFlag,

    @Relation(parentColumn = "alpha_code", entityColumn = "country_alpha_code")
    val currencies: List<Currency>,

    @Relation(parentColumn = "alpha_code", entityColumn = "country_alpha_code")
    val languages: List<Language>,

    @Relation(parentColumn = "alpha_code", entityColumn = "country_alpha_code")
    val timezones: List<Timezone>
)