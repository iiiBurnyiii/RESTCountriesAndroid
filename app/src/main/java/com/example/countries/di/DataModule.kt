package com.example.countries.di

import android.content.Context
import com.example.countries.data.api.CountriesApi
import com.example.countries.data.db.DatabaseHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideApi(): CountriesApi =
            CountriesApi.create()

    @Provides
    @Singleton
    fun provideDbHelper(context: Context): DatabaseHelper =
        DatabaseHelper(context)

}