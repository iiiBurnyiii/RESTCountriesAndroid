package com.example.countries.di

import com.example.countries.di.country.CountryViewModelModule
import com.example.countries.di.countryList.CountryListViewModelModule
import com.example.countries.ui.MainActivity
import com.example.countries.ui.country.CountryFragment
import com.example.countries.ui.countryList.CountryListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewInjectorModule {

    @ContributesAndroidInjector
    abstract fun injectMainActivity(): MainActivity

    @CountryListScope
    @ContributesAndroidInjector(modules = [CountryListViewModelModule::class])
    abstract fun injectCountryListFragment(): CountryListFragment

    @CountryScope
    @ContributesAndroidInjector(modules =  [CountryViewModelModule::class])
    abstract fun injectCountryFragment(): CountryFragment

}
