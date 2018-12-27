package com.example.countries.data.db.dao

import androidx.room.*
import com.example.countries.data.db.entity.TimezoneEntity
import io.reactivex.Single

@Dao
interface TimezoneDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(timezones: List<TimezoneEntity>)

    @Update
    fun update(timezones: List<TimezoneEntity>)

    @Query("""
        SELECT timezone FROM timezones LEFT JOIN join_entity ON timezone = timezone_code
        WHERE country_alpha_code = :alphaCode
    """)
    fun getTimezonesByAlphaCode(alphaCode: String): Single<List<TimezoneEntity>>

}