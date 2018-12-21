package com.example.countries.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.countries.model.CountryNameAndFlag
import com.example.countries.model.Currency
import com.example.countries.model.Language
import com.example.countries.model.Timezone

@Database(entities = [
    CountryNameAndFlag::class,
    Language::class,
    Currency::class,
    Timezone::class
], version = 1, exportSchema = false)
abstract class CountryListDatabase : RoomDatabase() {

    abstract fun countryListDao(): CountryListDao

    companion object {

        @Volatile private var INSTANCE: CountryListDatabase? = null

        fun getInstance(context: Context): CountryListDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                CountryListDatabase::class.java, "CountryList.db")
                .build()
    }

}