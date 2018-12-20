package com.example.countries.di.data

import com.example.countries.data.api.CountriesApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): CountriesApi =
            CountriesApi.create()

}