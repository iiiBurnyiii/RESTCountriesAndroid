package com.example.countries.di.ui

import com.example.countries.di.CountryListScope
import com.example.countries.ui.MainActivity
import com.example.countries.ui.countryList.CountryListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewInjectorModule {

    @ContributesAndroidInjector
    abstract fun injectMainActivity(): MainActivity

    @CountryListScope
    @ContributesAndroidInjector
    abstract fun injectCountryListFragment(): CountryListFragment

}
