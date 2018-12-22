package com.example.countries.di

import com.example.countries.data.CountryListRepository
import com.example.countries.data.IRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(repository: CountryListRepository): IRepository

}
