package com.example.countries.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.countries.data.db.entity.JoinEntity

@Dao
interface JoinDao {

    @Insert
    fun insert(join: JoinEntity)

    @Query("DELETE FROM join_entity")
    fun delete()

}