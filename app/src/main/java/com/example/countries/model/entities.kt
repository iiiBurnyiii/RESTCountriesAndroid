package com.example.countries.model

import androidx.room.*

@Entity(tableName = "countries")
data class CountryNameAndFlag(
    @PrimaryKey
    @ColumnInfo(name = "alpha_code")
    val alphaCode: String,
    val name: String,
    val flag: String
) {
    var alphaCodeWithName = "$alphaCode, $name"
}

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CountryNameAndFlag::class,
            parentColumns = ["alpha_code"],
            childColumns = ["country_alpha_code"]
        )
    ],
    primaryKeys = ["iso639", "country_alpha_code"],
    indices = [Index(value = ["country_alpha_code"], unique = false)]
)
data class Language(
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
    primaryKeys = ["timezone", "country_alpha_code"],
    indices = [Index(value = ["country_alpha_code"], unique = false)]
)
data class Timezone(
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
    primaryKeys = ["code", "country_alpha_code"],
    indices = [Index(value = ["country_alpha_code"], unique = false)]
)
data class Currency(
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