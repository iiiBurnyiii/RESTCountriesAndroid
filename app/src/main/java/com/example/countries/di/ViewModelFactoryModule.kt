package com.example.countries.di

import androidx.lifecycle.ViewModelProvider
import com.example.countries.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ViewModelFactoryModule {

    @Binds
    @Singleton
    abstract fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}
