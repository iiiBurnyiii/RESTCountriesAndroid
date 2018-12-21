package com.example.countries.di.countryList

import androidx.lifecycle.ViewModel
import com.example.countries.di.CountryListScope
import com.example.countries.di.ViewModelKey
import com.example.countries.ui.countryList.CountryListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CountryListViewModelModule {

    @Binds
    @IntoMap
    @CountryListScope
    @ViewModelKey(CountryListViewModel::class)
    abstract fun bindCountryListViewModel(viewModel: CountryListViewModel): ViewModel

}