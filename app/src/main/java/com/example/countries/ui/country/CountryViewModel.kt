package com.example.countries.ui.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.countries.data.CountriesRepository
import com.example.countries.model.CountryDetails
import javax.inject.Inject

class CountryViewModel @Inject constructor(
    val repository: CountriesRepository
) : ViewModel() {

    var countryLiveData: LiveData<CountryDetails>? = null

    val loadState = repository.countryLoadState

    fun start(alphaCode: String?) =
        alphaCode?.let {
             countryLiveData = repository.getCountry(alphaCode)
        }

    override fun onCleared() {
        repository.clear(this)
    }

}