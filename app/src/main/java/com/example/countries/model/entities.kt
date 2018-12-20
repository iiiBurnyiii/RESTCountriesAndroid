package com.example.countries.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = ["country_alpha_code",
        "language_id",
        "timezone_id",
        "currency_id"],
    foreignKeys = [
        ForeignKey(
            entity = CountryNameAndFlag::class,
            parentColumns = ["alpha_code"],
            childColumns = ["country_alpha_code"]
        ),
        ForeignKey(
            entity = Language::class,
            parentColumns = ["id"],
            childColumns = ["language_id"]
        ),
        ForeignKey(
            entity = Timezone::class,
            parentColumns = ["id"],
            childColumns = ["timezone_id"]
        ),
        ForeignKey(
            entity = Currency::class,
            parentColumns = ["id"],
            childColumns = ["currency_id"]
        )
    ]
)
data class CountryFiledsJoin(
    @ColumnInfo(name = "country_alpha_code")
    val countryAlphaCode: String,
    @ColumnInfo(name = "language_id")
    val languageId: Int,
    @ColumnInfo(name = "timezone_id")
    val timezoneId: Int,
    @ColumnInfo(name = "currency_id")
    val currencyId: Int
)

@Entity(tableName = "countries")
data class CountryNameAndFlag(
    @PrimaryKey
    @ColumnInfo(name = "alpha_code")
    val alphaCode: String,
    val name: String,
    val flag: String
)


@Entity
data class Language(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val nativeName: String
)


@Entity
data class Timezone(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val timezone: String
)


@Entity
data class Currency(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val code: String,
    val name: String,
    val symbol: Char
)