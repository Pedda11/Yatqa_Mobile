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

    //new login
    @Insert(onConflict = OnConflictStrategy.REPLACE) // in SQL INSERT
    suspend fun insert(login: Login)

    //update login
    @Update
    suspend fun update(login: Login)

    //get all logins
    @Query("SELECT * FROM Login")
    fun getAll(): LiveData<List<Login>>

    //get a single login by id
    @Query("SELECT * FROM Login WHERE id = :id")
    fun getById(id: Int): LiveData<Login>

    //delete a single login by id
    @Query("DELETE FROM Login WHERE id = :id")
    suspend fun deleteById(id: Int)

    //delete all
    @Query("DELETE from Login")
    suspend fun deleteAll()
}