package com.example.countries.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    @PrimaryKey
    val iso639: String,
    val name: String,
    val nativeName: String,
    @ColumnInfo(name = "country_alpha_code")
    val alphaCode: String
) : ICommonItem {
    override fun toCommonItem(): CommonItem =
        CommonItem("iso639_2: $iso639",
            "name: $name",
            "nativeName: $nativeName")
}


@Entity
data class Timezone(
    @PrimaryKey
    val timezone: String,
    @ColumnInfo(name = "country_alpha_code")
    val alphaCode: String
) : ICommonItem {
    override fun toCommonItem(): CommonItem =
        CommonItem(timezone)
}


@Entity
data class Currency(
    @PrimaryKey
    val code: String,
    val name: String,
    val symbol: String,
    @ColumnInfo(name = "country_alpha_code")
    val alphaCode: String
) : ICommonItem {
    override fun toCommonItem(): CommonItem =
        CommonItem("code: $code",
            "name: $name",
            "symbol: $symbol")
}