package com.example.countries.ui.countryList

import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.example.countries.data.CountriesRepository
import com.example.countries.util.LoadState
import com.example.countries.util.SingleLiveEvent
import javax.inject.Inject

class CountriesViewModel @Inject constructor(
    val repository: CountriesRepository
) : ViewModel() {

    val countryPagedList = repository.getCountries(10)

    val loadState = repository.listLoadState
    val isRefreshing = map(loadState) { it == LoadState.LOADING }!!

    val countryClickEvent = SingleLiveEvent<String>()

    fun refresh() {
        repository.needRefresh.value = true
        repository.loadCountries()
    }

    override fun onCleared() {
        repository.clear(this)
    }

}
