package com.example.countries.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.countries.util.LoadState
import com.example.countries.util.SingleLiveEvent
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable

data class CountriesResult(
    val countries: Flowable<PagedList<CountryTitle>>,
    val loadState: LiveData<LoadState>,
    val changeItemEvent: SingleLiveEvent<Int>,
    val snackbarMessageEvent: SingleLiveEvent<String>,
    val disposable: CompositeDisposable,
    val refresh: () -> Unit
)