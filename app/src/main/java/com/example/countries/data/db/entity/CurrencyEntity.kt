package com.example.countries.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.countries.model.CommonModel

@Entity(tableName = "currencies",
    indices = [Index("code")])
data class CurrencyEntity(
    @PrimaryKey
    val code: String,
    val name: String,
    val symbol: String
) : IEntity {

    override fun toCommonModel(): CommonModel =
        CommonModel(
            "code: $code",
            "name: $name",
            "symbol: $symbol"
        )

}