package com.example.yatqa_mobile.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yatqa_mobile.data.datamodels.Login
import com.example.yatqa_mobile.data.local.LoginDatabase
import com.github.theholywaffle.teamspeak3.TS3Api
import com.github.theholywaffle.teamspeak3.TS3Config
import com.github.theholywaffle.teamspeak3.TS3Query
import com.github.theholywaffle.teamspeak3.api.ServerInstanceProperty
import com.github.theholywaffle.teamspeak3.api.VirtualServerProperty
import com.github.theholywaffle.teamspeak3.api.wrapper.HostInfo
import com.github.theholywaffle.teamspeak3.api.wrapper.InstanceInfo
import com.github.theholywaffle.teamspeak3.api.wrapper.VirtualServer
import com.github.theholywaffle.teamspeak3.api.wrapper.VirtualServerInfo
import kotlin.random.Random

//tag for logging
const val TAG = "Repository"

class Repository(private val database: LoginDatabase) {

    //Get all data from Database
    var loginList: LiveData<List<Login>> = database.loginDatabaseDao.getAll()

    /**
     * ts3api as livedata
     */
    private val _ts3Api = MutableLiveData<TS3Api>()
    val ts3Api: LiveData<TS3Api>
        get() = _ts3Api

    //Insert call to database
    suspend fun insert(login: Login) {
        try {
            database.loginDatabaseDao.insert(login)
        } catch (e: Exception) {
            Log.e(TAG, "Error while insert in database: $e")
        }
    }

    //update call to database
    suspend fun update(login: Login) {
        try {
            database.loginDatabaseDao.update(login)
        } catch (e: Exception) {
            Log.e(TAG, "Error while updating in database: $e")
        }
    }

    //delete by id call to database
    suspend fun deleteLogin(login: Login) {
        try {
            database.loginDatabaseDao.deleteById(login.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error while deleting in database: $e")
        }
    }

    //telnet connect
    fun apiConnectTelnet(login: Login) {
        try {
            val tS3Conf = TS3Config()
            tS3Conf.setHost(login.ip)
            tS3Conf.setQueryPort(login.qPort)

            if (!(login.userName.isNullOrEmpty() && login.userPassword.isNullOrEmpty())) {
                tS3Conf.setLoginCredentials(login.userName, login.userPassword)
            }

            val ts3Query = TS3Query(tS3Conf)
            ts3Query.connect()

            _ts3Api.postValue(ts3Query.api)
        } catch (e: Exception) {
            Log.e(TAG, "Error while connect and login: $e")
        }
    }

    //connect to a singe virtual server
    fun apiConnectVirtualServer(vServer :VirtualServer) {
        _ts3Api.value?.selectVirtualServerByPort(vServer.port,
            "ApiTs3Bot${Random.nextInt(0, 1000)}"
        )
    }

    //get hostInfo
    fun apiGetGlobalData(): HostInfo? {
        return try {
            _ts3Api.value?.hostInfo
        } catch (e: Exception) {
            Log.e(TAG, "Error while getting HostInfo from _ts3Api: $e")
            null
        }
    }

    //get instanceInfo
    fun apiGetInstanceData(): InstanceInfo? {
        return try {
            _ts3Api.value?.instanceInfo
        } catch (e: Exception) {
            Log.e(TAG, "Error while getting InstanceInfo from _ts3Api: $e")
            null
        }
    }

    //get all virtual servers
    fun apiGetVServerList(): MutableList<VirtualServer>? {
        return try {
            _ts3Api.value?.virtualServers
        } catch (e: Exception) {
            Log.e(TAG, "Error while getting vServerList from _ts3Api: $e")
            return null
        }
    }

    //get virtualServerInfo
    fun apiGetVirtualServerInfo():VirtualServerInfo? {
        return _ts3Api.value?.serverInfo
    }

    fun setServerInstanceProperties(prop: ServerInstanceProperty, value: String) {
        _ts3Api.value?.editInstance(prop, value)
    }

    fun setVirtualServerProperties(prop: MutableMap<VirtualServerProperty,String>) {
        _ts3Api.value?.editServer(prop)
    }
}