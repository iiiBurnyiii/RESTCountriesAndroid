package com.example.countries.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.countries.model.CommonModel

@Entity(tableName = "languages",
    indices = [Index("iso639")])
data class LanguageEntity(
    @PrimaryKey
    val iso639: String,
    val name: String,
    @ColumnInfo(name = "native_name")
    val nativeName: String
) : IEntity {

    override fun toCommonModel(): CommonModel =
        CommonModel(
            "iso639_2: $iso639",
            "name: $name",
            "nativeName: $nativeName"
        )
}