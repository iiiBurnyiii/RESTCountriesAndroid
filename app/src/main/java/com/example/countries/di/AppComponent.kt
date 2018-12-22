package com.example.countries.di

import com.example.countries.CountryListApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    DataModule::class,
    RepositoryModule::class,
    ViewInjectorModule::class
])
interface AppComponent : AndroidInjector<CountryListApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<CountryListApplication>()

}