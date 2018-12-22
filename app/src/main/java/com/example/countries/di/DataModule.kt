package com.example.countries.di

import android.content.Context
import com.example.countries.data.api.CountryListApi
import com.example.countries.data.db.CountryListDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideApi(): CountryListApi =
            CountryListApi.create()

    @Provides
    @Singleton
    fun provideRoom(context: Context): CountryListDatabase =
        CountryListDatabase.getInstance(context)

}