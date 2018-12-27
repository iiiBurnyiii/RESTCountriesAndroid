package com.example.countries.ui.country

import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.example.countries.data.CountriesRepository
import javax.inject.Inject

class CountryViewModel @Inject constructor(
    val repository: CountriesRepository
) : ViewModel() {

    var countryDetails =
        map(repository.countryDetailsLiveData) { it }

    val loadState = repository.countryLoadState

    fun start(alphaCode: String?) =
        alphaCode?.let {
             repository.getCountry(alphaCode)
        }

    override fun onCleared() {
        repository.clear(this)
    }

}