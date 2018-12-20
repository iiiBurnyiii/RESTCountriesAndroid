package com.example.countries.di

import com.example.countries.CountriesApplication
import com.example.countries.di.data.RoomModule
import com.example.countries.di.ui.ViewInjectorModule
import com.example.countries.di.ui.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    RoomModule::class,
    ViewInjectorModule::class,
    ViewModelModule::class
])
interface AppComponent : AndroidInjector<CountriesApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<CountriesApplication>()

}