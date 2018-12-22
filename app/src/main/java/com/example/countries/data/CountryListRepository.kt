package com.example.countries.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import androidx.room.Transaction
import com.example.countries.data.api.CountryListApi
import com.example.countries.data.db.CountryListDatabase
import com.example.countries.model.Country
import com.example.countries.util.LoadState
import com.example.countries.util.toDbFriendly
import io.reactivex.Completable
import io.reactivex.Single
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
                { response ->
                    loadState.postValue(LoadState.LOADED)
                    Log.d(LOG_TAG, "countryList: $countryList")

                    insertCountriesToDb(countryList, needRefresh)
                },
                { e ->
                    loadState.postValue(LoadState.error("Please check your internet connection."))

                    Log.d(LOG_TAG, "Unable to load remote data: $e")
                }
            )
    }

    fun getCountryByAlphaCode(alphaCode: String): Single<Country> =
        db.countryListDao().getCountry(alphaCode)

    fun clear() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun insertCountriesToDb(countryList: List<Country>?, needRefresh: Boolean) {
        countryList?.let {
            loadState.postValue(LoadState.LOADING)
            compositeDisposable += Completable.fromAction {
                if (needRefresh) db.countryListDao().deleteAll()
                countryList.forEach { insertCountry(it) }
            }.subscribeOn(Schedulers.io())
                .subscribe(
                    { loadState.postValue(LoadState.LOADED) },
                    { e ->
                        loadState.postValue(LoadState.error("Unable to insert data to database."))

                        Log.d(LOG_TAG, "Unable to insert data to database: $e")
                    }
                )
        }
    }

    @Transaction
    private fun insertCountry(country: Country) =
        db.countryListDao().run {
            insert(country.countryNameAndFlag)
            insertCurrencies(country.currencies)
            insertLanguages(country.languages)
            insertTimezones(country.timezones)
        }

    companion object {
        private const val LOG_TAG = "RepositoryLogger"
    }

}