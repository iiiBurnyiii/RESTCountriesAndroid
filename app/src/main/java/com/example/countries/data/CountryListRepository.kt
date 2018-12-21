package com.example.countries.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.countries.data.api.CountryListApi
import com.example.countries.data.db.CountryListDatabase
import com.example.countries.model.Country
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

    private val compositeDisposable = CompositeDisposable()

    val loadState = MutableLiveData<LoadState>()

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

        compositeDisposable += api.getCountryList()
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
                    loadState.postValue(LoadState.ERROR)

                    Log.d(LOG_TAG, "Unable to load remote data: $e")
                }
            )
    }

    fun clear() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun insertCountriesToDb(countryList: List<Country>?, needRefresh: Boolean) {
        countryList?.let {
            loadState.postValue(LoadState.LOADING)

            compositeDisposable += db.countryListDao().insertCountryList(countryList, needRefresh)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { loadState.postValue(LoadState.LOADED) },
                    { e ->
                        loadState.postValue(LoadState.ERROR)

                        Log.d(LOG_TAG, "Unable to insert data to database: $e")
                    }
                )
        }
    }

    companion object {

        private const val LOG_TAG = "RepositoryLogger"

    }

}