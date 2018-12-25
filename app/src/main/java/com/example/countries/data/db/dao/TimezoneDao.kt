package com.example.countries.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.countries.data.db.entity.Timezone
import io.reactivex.Single

@Dao
interface TimezoneDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(timezone: Timezone)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(timezones: List<Timezone>)

    @Query("""
        SELECT timezone FROM timezones LEFT JOIN join_entity ON timezone = timezone_code
        WHERE country_alpha_code = :alphaCode
    """)
    fun getTimezonesByAlphaCode(alphaCode: String): Single<List<Timezone>>

}