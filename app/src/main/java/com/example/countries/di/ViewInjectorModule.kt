package com.example.countries.di

import androidx.lifecycle.ViewModelProvider
import com.example.countries.di.country.CountryViewModelModule
import com.example.countries.di.countryList.CountryListViewModelModule
import com.example.countries.ui.MainActivity
import com.example.countries.ui.ViewModelFactory
import com.example.countries.ui.country.CountryFragment
import com.example.countries.ui.countryList.CountriesFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class ViewInjectorModule {

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector
    abstract fun injectMainActivity(): MainActivity

    @CountryListScope
    @ContributesAndroidInjector(modules = [CountryListViewModelModule::class])
    abstract fun injectCountryListFragment(): CountriesFragment

    @CountryScope
    @ContributesAndroidInjector(modules =  [CountryViewModelModule::class])
    abstract fun injectCountryFragment(): CountryFragment

}
