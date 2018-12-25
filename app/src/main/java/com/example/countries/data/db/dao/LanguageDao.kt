package com.example.countries.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.countries.data.db.entity.Language
import io.reactivex.Single

@Dao
interface LanguageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(language: Language)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(languages: List<Language>)

    @Query("""
        SELECT code, name, native_name
        FROM languages JOIN join_entity
        ON languages.code = join_entity.language_code
        WHERE join_entity.country_alpha_code = :alphaCode
    """)
    fun getLanguagesByAlphaCode(alphaCode: String): Single<List<Language>>

}