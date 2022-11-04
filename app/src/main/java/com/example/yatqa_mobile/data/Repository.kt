package com.example.yatqa_mobile.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yatqa_mobile.data.datamodels.Login
import com.example.yatqa_mobile.data.local.LoginDatabase
import com.github.theholywaffle.teamspeak3.TS3Api
import com.github.theholywaffle.teamspeak3.TS3Config
import com.github.theholywaffle.teamspeak3.TS3Query
import com.github.theholywaffle.teamspeak3.api.wrapper.HostInfo
import com.github.theholywaffle.teamspeak3.api.wrapper.InstanceInfo

const val TAG = "Repository"

class Repository(private val database: LoginDatabase) {

    //Get all data from Database
    val loginList: LiveData<List<Login>> = database.loginDatabaseDao.getAll()

    private val _ts3Api = MutableLiveData<TS3Api>()
    val ts3Api: LiveData<TS3Api>
        get() = _ts3Api

    //Insert a new login to database
    suspend fun insert(login: Login) {
        try {
            database.loginDatabaseDao.insert(login)
        } catch (e: Exception) {
            Log.e(TAG, "Error while insert in database: $e")
        }
    }

    suspend fun update(login: Login) {
        try {
            database.loginDatabaseDao.update(login)
        } catch (e: Exception) {
            Log.e(TAG, "Error while updating in database: $e")
        }
    }

    suspend fun deleteLogin(login: Login) {
        try {
            database.loginDatabaseDao.deleteById(login.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error while deleting in database: $e")
        }
    }

    fun apiConnect(login: Login) {
        try {
            val ts3 = TS3Config()
            ts3.setHost(login.ip)
            ts3.setQueryPort(login.qPort)
            ts3.setLoginCredentials(login.userName, login.userPassword)

            val ts3Query = TS3Query(ts3)
            ts3Query.connect()

            _ts3Api.postValue(ts3Query.api)

            _ts3Api.value?.login(login.userName, login.userPassword)
        } catch (e: Exception) {
            Log.e(TAG, "Error while connect and login: $e")
        }
    }

    fun apiGetGlobalData(): HostInfo? {
        return try {
            _ts3Api.value?.hostInfo
        } catch (e: Exception) {
            Log.e(TAG, "Error while getting HostInfo from _ts3Api: $e")
            null
        }
    }

    fun apiGetInstanceData(): InstanceInfo? {
        return try {
            _ts3Api.value?.instanceInfo
        } catch (e: Exception) {
            Log.e(TAG, "Error while getting InstanceInfo from _ts3Api: $e")
            null
        }
    }
}