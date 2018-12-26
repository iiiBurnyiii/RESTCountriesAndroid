package com.example.countries.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class Country(
    @PrimaryKey
    @ColumnInfo(name = "alpha_code")
    val alphaCode: String,
    val name: String,
    val flag: String
)
