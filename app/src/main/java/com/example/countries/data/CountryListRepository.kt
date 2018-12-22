package com.example.countries.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.countries.data.api.CountryListApi
import com.example.countries.data.db.CountryListDatabase
import com.example.countries.model.Country
import com.example.countries.ui.country.CountryViewModel
import com.example.countries.ui.countryList.CountryListViewModel
import com.example.countries.util.LoadState
import com.example.countries.util.toDbFriendly
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import javax.inject.Inject

class CountryListRepository @Inject constructor(
    val db: CountryListDatabase,
    val api: CountryListApi
) {

    private val countryListDisposable = CompositeDisposable()
    private val countryDisposable = CompositeDisposable()

    val loadState = MutableLiveData<LoadState>()
    val countryLiveData = MutableLiveData<Country>()

    fun getCountryPagedList(listPageSize: Int): LiveData<PagedList<Country>> {
        val boundaryCallback = CountryListBoundaryCallback(
            loadData = this::loadCountries
        )

        return db.countryListDao().getAllCountries().toLiveData(
            config = Config(
                pageSize = listPageSize,
                initialLoadSizeHint = listPageSize * 2,
                enablePlaceholders = true
            ),
            boundaryCallback = boundaryCallback,
            fetchExecutor = Executors.newSingleThreadExecutor()
        )
    }

    fun loadCountries(needRefresh: Boolean) {
        loadState.postValue(LoadState.LOADING)

        countryListDisposable += api.getCountryList()
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.map { it.toDbFriendly() }
            }
            .subscribe(
                { countryList ->
                    loadState.postValue(LoadState.LOADED)

                    insertCountriesToDb(countryList, needRefresh)
                },
                { e ->
                    loadState.postValue(LoadState.error("Please check your internet connection."))

                    Log.d(LOG_TAG, "Unable to load remote data: $e")
                }
            )
    }

    fun loadCountry(alphaCode: String) {
        countryDisposable += db.countryListDao().getCountry(alphaCode)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { country ->
                    countryLiveData.postValue(country)
                },
                { e ->
                    loadState.postValue(LoadState.error("Unable to load data from database."))

                    Log.d(LOG_TAG, "Unable to load country: $e")
                }
            )
    }

    fun clear(viewModel: ViewModel) {
        when (viewModel) {
            is CountryListViewModel -> disposeCountryList()
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

    private fun insertCountriesToDb(countryList: List<Country>?, needRefresh: Boolean) {
        countryList?.let {
            loadState.postValue(LoadState.LOADING)

            db.countryListDao().insertCountryList(countryList, needRefresh)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { loadState.postValue(LoadState.LOADED) },
                    { e ->
                        loadState.postValue(LoadState.error("Unable to insert data to database."))

                        Log.d(LOG_TAG, "Unable to insert data to database: $e")
                    }
                )
        }
    }

    companion object {
        private const val LOG_TAG = "RepositoryLogger"
    }

}