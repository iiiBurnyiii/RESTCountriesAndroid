package com.example.countries.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.countries.data.api.CountriesApi
import com.example.countries.data.api.CountryResponse
import com.example.countries.data.db.DatabaseHelper
import com.example.countries.domain.ResponseParser
import com.example.countries.model.CountryDetails
import com.example.countries.model.CountryTitle
import com.example.countries.ui.country.CountryViewModel
import com.example.countries.ui.countryList.CountriesViewModel
import com.example.countries.util.LoadState
import com.example.countries.util.SingleLiveEvent
import com.google.gson.JsonSyntaxException
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import javax.inject.Inject

class CountriesRepository @Inject constructor(
    val helper: DatabaseHelper,
    val api: CountriesApi
): IRepository {

    private val countryListDisposable = CompositeDisposable()
    val listLoadState = SingleLiveEvent<LoadState>()
    val needRefresh = MutableLiveData<Boolean>()

    private val countryDisposable = CompositeDisposable()
    val countryLoadState = SingleLiveEvent<LoadState>()
    val countryDetailsLiveData = MutableLiveData<CountryDetails>()

    override fun loadCountries() {
        listLoadState.postValue(LoadState.LOADING)

        countryListDisposable += api.loadCountriesWithFilter()
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { response ->
                    handleResponse(response)
                    listLoadState.postValue(LoadState.LOADING.apply {
                        msg = "Remote data loaded."
                    })
                },
                onError = { e ->
                    val loadError = when (e) {
                        is JsonSyntaxException -> LoadState.ERROR.apply {
                            msg = "Unable to parse response."
                        }
                        else -> LoadState.ERROR.apply {
                            msg = "Please check your internet connection."
                        }
                    }
                    listLoadState.postValue(loadError)
                    Log.d(LOG_TAG, "Unable to load remote data: $e")
                }
            )
    }

    override fun getCountries(listPageSize: Int): LiveData<PagedList<CountryTitle>> {
        val boundaryCallback = CountryListBoundaryCallback(
            loadData = this::loadCountries
        )

        return helper.getCountries().toLiveData(
            config = Config(
                pageSize = listPageSize,
                initialLoadSizeHint = listPageSize * 2,
                enablePlaceholders = true
            ),
            boundaryCallback = boundaryCallback,
            fetchExecutor = Executors.newSingleThreadExecutor()
        )
    }

    override fun getCountry(alphaCode: String) {
        countryDisposable += helper.getCountryDetails(alphaCode)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { country ->
                    countryDetailsLiveData.postValue(country)
                },
                onError = { e ->
                    countryLoadState.postValue(LoadState.ERROR.apply {
                        msg = "Unable to load body from database."
                    })

                    Log.d(LOG_TAG, "Unable to load country: $e")
                }
            )
    }

    override fun clear(viewModel: ViewModel) {
        when (viewModel) {
            is CountriesViewModel -> disposeCountryList()
            is CountryViewModel -> disposeCountry()
        }
    }

    private fun handleResponse(response: List<CountryResponse>) {
        countryListDisposable += ResponseParser(response).parse(
            doSomeWithResult = { dtoList ->
                countryListDisposable +=
                        helper.handleData(dtoList, needRefresh.value ?: false,
                            listLoadState)
            },
            doSomeWithError = { e ->
                countryLoadState.postValue(LoadState.ERROR.apply {
                    msg = "Handle response error."
                })
                Log.d(LOG_TAG, "Handle response error: $e")
            }
        )
    }

    private fun disposeCountryList() {
        if (!countryListDisposable.isDisposed) {
            countryListDisposable.dispose()
        }
    }

    private fun disposeCountry() {
        if (!countryDisposable.isDisposed) {
            countryDisposable.dispose()
        }
    }

    companion object {
        private const val LOG_TAG = "RepositoryLogger"
    }

}