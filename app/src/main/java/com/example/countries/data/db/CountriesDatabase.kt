package com.example.countries.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.countries.data.db.dao.*
import com.example.countries.data.db.entity.*

@Database(entities = [
    JoinEntity::class,
    CountryEntity::class,
    LanguageEntity::class,
    CurrencyEntity::class,
    TimezoneEntity::class
], version = 1, exportSchema = false)
abstract class CountriesDatabase : RoomDatabase() {

    abstract fun joinDao(): JoinDao
    abstract fun countryDao(): CountryDao
    abstract fun languageDao(): LanguageDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun timezoneDao(): TimezoneDao

    companion object {

        @Volatile private var INSTANCE: CountriesDatabase? = null

        fun getInstance(context: Context): CountriesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                CountriesDatabase::class.java, "CountryList.helper")
                .build()
    }

}