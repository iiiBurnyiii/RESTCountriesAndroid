package com.example.countries.di.data

import android.content.Context
import com.example.countries.data.db.CountryListDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideRoom(context: Context): CountryListDatabase =
            CountryListDatabase.getInstance(context)

}
