package com.example.countries.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.countries.data.db.entity.LanguageEntity
import io.reactivex.Single

@Dao
interface LanguageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(languages: List<LanguageEntity>)

    @Query("""
        SELECT iso639, name, native_name
        FROM languages JOIN join_entity
        ON languages.iso639 = join_entity.language_code
        WHERE join_entity.country_alpha_code = :alphaCode
    """)
    fun getLanguagesByAlphaCode(alphaCode: String): Single<List<LanguageEntity>>

    @Query("DELETE FROM languages")
    fun delete()

}