package com.example.countries.domain

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.paging.Config
import androidx.paging.toFlowable
import com.example.countries.data.api.CountriesApi
import com.example.countries.data.db.CountriesDatabase
import com.example.countries.model.CountriesResult
import com.example.countries.model.CountryResult
import com.example.countries.util.LoadState
import com.example.countries.util.SingleLiveEvent
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountriesRepository @Inject constructor(
    val db: CountriesDatabase,
    val api: CountriesApi,
    val context: Context
): IRepository {

    private var helper = DatabaseHelper(db)

    private val countriesDisposable = CompositeDisposable()
    private val loadState = MutableLiveData<LoadState>()
    private val changeItemEvent = SingleLiveEvent<Int>()
    private val snackbarMessageEvent = SingleLiveEvent<String>()

    private fun loadCountries() {
        loadState.postValue(LoadState.LOADING)

        countriesDisposable += api.loadCountriesWithFilter().flatMapCompletable { response ->
            ResponseTransformer(response).transform().map { data ->
                loadFlags(ResponseTransformer.getFlagUrlList())

                helper.insert(data)
            }.ignoreElement()
        }.subscribeOn(Schedulers.io())
            .subscribeBy(
                onComplete = {
                    loadState.postValue(LoadState.LOADED)
                    snackbarMessageEvent.postValue("Countries loaded")
                },
                onError = { e ->
                    loadState.postValue(LoadState.ERROR)
                    snackbarMessageEvent.postValue("Countries loading failed: $e")
                }
            )
    }

    private fun refresh() {
        loadState.postValue(LoadState.LOADING)

        countriesDisposable += api.loadCountriesWithFilter().flatMapCompletable { response ->
            ResponseTransformer(response).transform().map { data ->
                val flagList = ResponseTransformer.getFlagUrlList()
                removeFlags(flagList)
                loadFlags(flagList)

                helper.refresh(data)
            }.ignoreElement()
        }.subscribeOn(Schedulers.io())
            .subscribeBy(
                onComplete = {
                    loadState.postValue(LoadState.LOADED)
                    snackbarMessageEvent.postValue("Countries refreshed")
                },
                onError = { e ->
                    loadState.postValue(LoadState.ERROR)
                    snackbarMessageEvent.postValue("Countries refresh failed: $e")
                }
            )
    }

    override fun getCountries(listPageSize: Int): CountriesResult {
        val boundaryCallback = CountryListBoundaryCallback(
            loadCountries = this::loadCountries
        )

        val countries = helper.getCountries().toFlowable(
            config = Config(
                pageSize = listPageSize,
                initialLoadSizeHint = listPageSize * 2,
                enablePlaceholders = true),
            boundaryCallback = boundaryCallback,
            backpressureStrategy = BackpressureStrategy.LATEST)

        return CountriesResult(
            countries = countries,
            loadState = map(loadState) { it },
            changeItemEvent = changeItemEvent,
            snackbarMessageEvent = snackbarMessageEvent,
            disposable = countriesDisposable,
            refresh = this::refresh
        )
    }

    override fun getCountry(alphaCode: String): CountryResult {
        val countryLoadState = MutableLiveData<LoadState>()
        val country = helper.getCountryDetails(alphaCode)

        return CountryResult(
            country = country,
            loadState = map(countryLoadState) { it }
        )
    }

    private fun loadFlags(flagUrl: List<String>) {
        val loader = ImageLoader(flagUrl, context)
        countriesDisposable += loader.start(
            doSomeOnNext = { itemPosition ->
                changeItemEvent.postValue(itemPosition)
            },
            doSomeOnComplete = {
                snackbarMessageEvent.postValue("Flags loaded")
            },
            doSomeOnError = { e ->
                snackbarMessageEvent.postValue("Flags load failed: $e")
            }
        )
    }

    private fun removeFlags(flagUrlList: List<String>) {
        val loader = ImageLoader(flagUrlList, context)
        countriesDisposable += loader.clear(
            doSomeOnComplete = { },
            doSomeOnError = { e ->
                snackbarMessageEvent.postValue("Old flag remove failed: $e")
            }
        )
    }

}