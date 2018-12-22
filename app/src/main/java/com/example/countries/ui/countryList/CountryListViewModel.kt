package com.example.countries.ui.countryList

import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.example.countries.data.CountryListRepository
import com.example.countries.util.LoadState
import com.example.countries.util.SingleLiveEvent
import javax.inject.Inject

class CountryListViewModel @Inject constructor(
    val repository: CountryListRepository
) : ViewModel() {

    val countryPagedList = repository.getCountryPagedList(10)

    val loadState = repository.listLoadState
    val isRefreshing = map(loadState) { it == LoadState.LOADING }!!

    internal val countryClickEvent = SingleLiveEvent<String>()

    fun refresh() {
        repository.loadCountries(true)
    }

    override fun onCleared() {
        repository.clear(this)
    }

}
