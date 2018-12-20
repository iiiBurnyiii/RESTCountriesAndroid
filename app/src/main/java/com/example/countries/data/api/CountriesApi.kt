package com.example.countries.data.api

import android.util.Log
import com.example.countries.model.Country
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CountriesApi {

    @GET("all")
    fun getCountryList(
        @Query("fields") vararg fields: String
    ): Single<List<Country>>

    companion object {
        private const val BASE_URL = "https://restcountries.eu/rest/v2/"

        fun create(): CountriesApi {
            val logger =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                    Log.d("ApiLogger", it)
                }).apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CountriesApi::class.java)
        }

    }

}