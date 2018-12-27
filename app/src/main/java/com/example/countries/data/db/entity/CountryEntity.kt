package com.example.countries.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    @ColumnInfo(name = "alpha_code")
    val alphaCode: String,
    val name: String,
    val flagUri: String
)
