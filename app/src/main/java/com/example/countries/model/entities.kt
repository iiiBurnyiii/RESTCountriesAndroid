package com.example.countries.model

import androidx.room.*

@Entity(tableName = "countries")
data class CountryNameAndFlag(
    @PrimaryKey
    @ColumnInfo(name = "alpha_code")
    val alphaCode: String,
    val name: String,
    val flag: String
)


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CountryNameAndFlag::class,
            parentColumns = ["alpha_code"],
            childColumns = ["country_alpha_code"]
        )
    ],
    indices = [Index(value = ["country_alpha_code"])]
)
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


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CountryNameAndFlag::class,
            parentColumns = ["alpha_code"],
            childColumns = ["country_alpha_code"]
        )
    ],
    indices = [Index(value = ["country_alpha_code"])]
)
data class Timezone(
    @PrimaryKey
    val timezone: String,
    @ColumnInfo(name = "country_alpha_code")
    val alphaCode: String
) : ICommonItem {
    override fun toCommonItem(): CommonItem =
        CommonItem(timezone)
}


@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CountryNameAndFlag::class,
            parentColumns = ["alpha_code"],
            childColumns = ["country_alpha_code"]
        )
    ],
    indices = [Index(value = ["country_alpha_code"])]
)
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