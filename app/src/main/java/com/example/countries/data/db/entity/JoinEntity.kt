package com.example.countries.data.db.entity

import androidx.room.*

@Entity(tableName = "join_entity",
    foreignKeys = [
        ForeignKey(entity = CountryTitle::class,
            parentColumns = ["alpha_code"],
            childColumns = ["country_alpha_code"],
            deferred = true,
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE),
        ForeignKey(entity = Language::class,
            parentColumns = ["code"],
            childColumns = ["language_code"],
            deferred = true,
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE),
        ForeignKey(entity = Currency::class,
            parentColumns = ["code"],
            childColumns = ["currency_code"],
            deferred = true,
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE),
        ForeignKey(entity = Timezone::class,
            parentColumns = ["timezone"],
            childColumns = ["timezone_code"],
            deferred = true,
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE)
    ],
    indices = [
        Index("country_alpha_code"),
        Index("language_code"),
        Index("currency_code"),
        Index("timezone_code")
    ])
data class JoinEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "country_alpha_code")
    val countryAlphaCode: String,
    @ColumnInfo(name = "language_code")
    val languageCode: String?,
    @ColumnInfo(name = "currency_code")
    val currencyCode: String?,
    @ColumnInfo(name = "timezone_code")
    val timezoneCode: String?
)