package com.example.countries.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.countries.data.db.entity.TimezoneEntity
import io.reactivex.Single

@Dao
interface TimezoneDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(timezones: List<TimezoneEntity>)

    @Query("""
        SELECT timezone FROM timezones LEFT JOIN join_entity ON timezone = timezone_code
        WHERE country_alpha_code = :alphaCode
    """)
    fun getTimezonesByAlphaCode(alphaCode: String): Single<List<TimezoneEntity>>

    @Query("DELETE FROM timezones")
    fun delete()

}