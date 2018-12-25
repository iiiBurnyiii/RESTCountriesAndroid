package com.example.countries.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.countries.data.api.CountriesApi
import com.example.countries.data.db.DatabaseHelper
import com.example.countries.data.db.entity.CountryTitle
import com.example.countries.model.CountryModel
import com.example.countries.ui.country.CountryViewModel
import com.example.countries.ui.countryList.CountriesViewModel
import com.example.countries.util.LoadState
import com.example.countries.util.SingleLiveEvent
import com.example.countries.util.toCountryModelList
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

    private val countryDisposable = CompositeDisposable()
    val countryLoadState = SingleLiveEvent<LoadState>()

    override fun loadCountries(needRefresh: Boolean) {
        listLoadState.postValue(LoadState.LOADING)

        countryListDisposable += api.loadCountriesWithFilter()
            .subscribeOn(Schedulers.io())
            .map { response -> response.toCountryModelList() }
            .subscribeBy(
                onSuccess = { countries ->
                    listLoadState.postValue(LoadState.LOADING.apply { msg = "Remote data loaded." })
                    setIntoDb(countries, needRefresh)
                },
                onError = { e ->
                    val loadError = when (e) {
                        is JsonSyntaxException -> LoadState.ERROR.apply { msg = "Unable to parse response." }
                        else -> LoadState.ERROR.apply { msg = "Please check your internet connection." }
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

    override fun getCountry(alphaCode: String): LiveData<CountryModel> {
        val countryLiveData = MutableLiveData<CountryModel>()

        countryDisposable += helper.getCountry(alphaCode)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { country ->
                    countryLiveData.postValue(country)
                },
                onError = { e ->
                    countryLoadState.postValue(LoadState.ERROR.apply { msg = "Unable to load data from database." })

                    Log.d(LOG_TAG, "Unable to load country: $e")
                }
            )

        return map(countryLiveData) { it }
    }

    override fun clear(viewModel: ViewModel) {
        when (viewModel) {
            is CountriesViewModel -> disposeCountryList()
            is CountryViewModel -> disposeCountry()
        }
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

    private fun setIntoDb(countries: List<CountryModel>?, needRefresh: Boolean) {
        countries?.let {
            listLoadState.postValue(LoadState.LOADING)

            if (needRefresh) {
                helper.update(countries)
            } else {
                helper.insert(countries)
            }.subscribeOn(Schedulers.io())
                .subscribeBy(
                    onComplete = {
                        listLoadState.postValue(LoadState.LOADED.apply { msg = "Data inserted to database." })
                    },
                    onError = { e ->
                        listLoadState.postValue(LoadState.ERROR.apply { msg = "Unable to insert data to database." })

                        Log.d(LOG_TAG, "Unable to insert data to database: $e")
                    }
                )
        }
    }

    companion object {
        private const val LOG_TAG = "RepositoryLogger"
    }

}