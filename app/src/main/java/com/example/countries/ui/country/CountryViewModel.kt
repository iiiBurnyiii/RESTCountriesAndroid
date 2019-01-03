package com.example.countries.ui.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.example.countries.domain.CountriesRepository
import com.example.countries.model.CountryDetails
import com.example.countries.util.LoadState
import javax.inject.Inject

class CountryViewModel @Inject constructor(
    val repository: CountriesRepository
) : ViewModel() {

    private val alphaCode: MutableLiveData<String> = MutableLiveData()
    private var countryResult = map(alphaCode) {
        repository.getCountry(it)
    }

    val countryDetails: LiveData<CountryDetails> = switchMap(countryResult) {
        LiveDataReactiveStreams.fromPublisher(it.country.toFlowable())
    }
    val loadState: LiveData<LoadState> = switchMap(countryResult) {
        it.loadState
    }
    
    fun start(alphaCode: String?) {
        this.alphaCode.value = alphaCode
    }

}