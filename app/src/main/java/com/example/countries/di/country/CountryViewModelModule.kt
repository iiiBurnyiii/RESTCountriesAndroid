package com.example.countries.di.country

import androidx.lifecycle.ViewModel
import com.example.countries.di.CountryScope
import com.example.countries.di.ViewModelKey
import com.example.countries.ui.country.CountryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CountryViewModelModule {

    @Binds
    @IntoMap
    @CountryScope
    @ViewModelKey(CountryViewModel::class)
    abstract fun bindCountryListViewModel(viewModel: CountryViewModel): ViewModel

}
