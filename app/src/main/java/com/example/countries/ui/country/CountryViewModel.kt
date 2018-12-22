package com.example.countries.ui.country

import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.example.countries.data.CountryListRepository
import javax.inject.Inject

class CountryViewModel @Inject constructor(
    val repository: CountryListRepository
) : ViewModel() {

    private val countryLiveData = repository.countryLiveData

    val country = map(countryLiveData) { it.countryNameAndFlag }!!
    val currencies = map(countryLiveData) { it.currencies }!!
    val timezones = map(countryLiveData) { it.timezones }!!
    val languages = map(countryLiveData) { it.languages }!!

    val loadState = repository.countryLoadState

    fun start(alphaCode: String?) =
        alphaCode?.let {
            repository.getCountry(alphaCode)
        }

    override fun onCleared() {
        repository.clear(this)
    }

}