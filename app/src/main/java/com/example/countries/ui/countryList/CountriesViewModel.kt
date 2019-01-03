package com.example.countries.ui.countryList

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.example.countries.domain.CountriesRepository
import com.example.countries.util.LoadState
import com.example.countries.util.SingleLiveEvent
import javax.inject.Inject

class CountriesViewModel @Inject constructor(
    repository: CountriesRepository
) : ViewModel() {

    private val countryResult = repository.getCountries(30)
    private val disposable = countryResult.disposable

    val countryPagedList = LiveDataReactiveStreams.fromPublisher(countryResult.countries)

    val loadState = countryResult.loadState
    val isRefreshing = map(loadState) { it == LoadState.LOADING }!!

    val countryClickEvent = SingleLiveEvent<String>()
    val changeItemEvent = countryResult.changeItemEvent
    val snackbarMessageEvent = countryResult.snackbarMessageEvent

    fun refresh() {
        countryResult.refresh()
    }

    override fun onCleared() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

}
