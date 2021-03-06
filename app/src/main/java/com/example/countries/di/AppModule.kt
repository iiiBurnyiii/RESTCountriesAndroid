package com.example.countries.di

import android.content.Context
import com.example.countries.CountryListApplication
import com.example.countries.data.api.CountriesApi
import com.example.countries.data.db.CountriesDatabase
import com.example.countries.domain.CountriesRepository
import com.example.countries.domain.IRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: CountryListApplication): Context =
            application.applicationContext

    @Provides
    @Singleton
    fun provideApi(): CountriesApi =
        CountriesApi.create()

    @Provides
    @Singleton
    fun provideDb(context: Context): CountriesDatabase =
        CountriesDatabase.getInstance(context)

    @Provides
    @Singleton
    fun bindRepository(repository: CountriesRepository): IRepository = repository

}
