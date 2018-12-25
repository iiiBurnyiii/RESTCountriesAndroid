package com.example.countries.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.countries.ui.country.ICommonModel

@Entity(tableName = "languages",
    indices = [Index("code")])
data class Language(
    @PrimaryKey
    val code: String,
    val name: String,
    @ColumnInfo(name = "native_name")
    val nativeName: String
) : ICommonModel {
    override fun toCommonModel(): CommonModel =
        CommonModel(
            "iso639_2: $code",
            "name: $name",
            "nativeName: $nativeName"
        )
}