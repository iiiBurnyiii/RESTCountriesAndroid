package com.example.countries.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.countries.data.db.JoinEntity

@Dao
interface JoinDao {

    @Insert
    fun insert(join: JoinEntity)

    @Update
    fun update(join: JoinEntity)

    @Delete
    fun delete(join: JoinEntity)

}