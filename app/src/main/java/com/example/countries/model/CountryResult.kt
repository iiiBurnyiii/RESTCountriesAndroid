package com.example.countries.model

import androidx.lifecycle.LiveData
import com.example.countries.util.LoadState
import io.reactivex.Single

data class CountryResult(
    val country: Single<CountryDetails>,
    val loadState: LiveData<LoadState>
)