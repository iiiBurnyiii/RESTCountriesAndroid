package com.example.countries.ui.country

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countries.data.CountryListRepository
import com.example.countries.util.LoadState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountryViewModel @Inject constructor(
    val repository: CountryListRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val loadState = MutableLiveData<LoadState>()

    val flag = MutableLiveData<String>()
    val name = MutableLiveData<String>()

    fun loadCountry(alphaCode: String) {
        loadState.postValue(LoadState.LOADING)
        compositeDisposable += repository.getCountryByAlphaCode(alphaCode)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { country ->
                    loadState.postValue(LoadState.LOADED)

                    flag.postValue(country.countryNameAndFlag.flag)
                    name.postValue(country.countryNameAndFlag.name)
                },
                { e ->
                    loadState.postValue(LoadState.error("Unable to load country."))

                    Log.d(LOG_TAG, "Unable to load country: $e")
                }
            )

    }

    companion object {
        private const val LOG_TAG = "CountryViewModelLogger"
    }

}