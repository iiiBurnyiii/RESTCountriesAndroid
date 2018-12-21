package com.example.countries.di.data

import com.example.countries.data.api.CountryListApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideApi(): CountryListApi =
            CountryListApi.create()

}