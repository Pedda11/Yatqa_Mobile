package com.example.yatqa_mobile.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.yatqa_mobile.data.datamodels.Login
import com.example.yatqa_mobile.data.local.LoginDatabase

const val TAG = "Repository"

class Repository(private val database: LoginDatabase) {

    //Get all data from Database
    val loginList : LiveData<List<Login>> = database.loginDatabaseDao.getAll()

    //Insert a new login to database
    suspend fun insert(login: Login){
        try {
            database.loginDatabaseDao.insert(login)
        }catch (e: Exception){
            Log.e(TAG, "Error while insert in database: $e")
        }
    }

    suspend fun update(login: Login){
        try {
            database.loginDatabaseDao.update(login)
        }catch (e: Exception){
            Log.e(TAG, "Error while updating in database: $e")
        }
    }

    suspend fun deleteLogin(login: Login){
        try {
            database.loginDatabaseDao.deleteById(login.id)
        }catch (e: Exception){
            Log.e(TAG, "Error while deleting in database: $e")
        }
    }
}