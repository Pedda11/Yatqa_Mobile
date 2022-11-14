package com.example.yatqa_mobile.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.yatqa_mobile.data.datamodels.Login

@Dao
interface LoginDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // in SQL INSERT
    suspend fun insert(login: Login)

    @Update
    suspend fun update(login: Login)

    @Query("SELECT * FROM Login")
    fun getAll(): LiveData<List<Login>>

    @Query("SELECT * FROM Login WHERE id = :id")
    fun getById(id: Int): LiveData<Login>

    @Query("DELETE FROM Login WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE from Login")
    suspend fun deleteAll()
}