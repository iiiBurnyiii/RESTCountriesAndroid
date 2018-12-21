package com.example.countries.di

import com.example.countries.CountryListApplication
import com.example.countries.di.data.ApiModule
import com.example.countries.di.data.RoomModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    RoomModule::class,
    ApiModule::class,
    ViewInjectorModule::class,
    ViewModelFactoryModule::class
])
interface AppComponent : AndroidInjector<CountryListApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<CountryListApplication>()

}