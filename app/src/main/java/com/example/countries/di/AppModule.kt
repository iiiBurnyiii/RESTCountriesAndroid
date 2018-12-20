package com.example.countries.di

import android.content.Context
import com.example.countries.CountriesApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: CountriesApplication): Context =
            application.applicationContext

}
