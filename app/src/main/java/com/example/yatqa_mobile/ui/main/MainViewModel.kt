package com.example.yatqa_mobile.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.yatqa_mobile.data.Repository
import com.example.yatqa_mobile.data.TAG
import com.example.yatqa_mobile.data.datamodels.Login
import com.example.yatqa_mobile.data.local.getDatabase
import com.github.theholywaffle.teamspeak3.api.wrapper.HostInfo
import com.github.theholywaffle.teamspeak3.api.wrapper.InstanceInfo
import com.github.theholywaffle.teamspeak3.api.wrapper.VirtualServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    //set the variables for database and repository
    private val database = getDatabase(application)
    private val repository = Repository(database)

    val loginList = repository.loginList
    lateinit var hostInfo: HostInfo
    lateinit var instanceInfo: InstanceInfo
    lateinit var vServerList: MutableList<VirtualServer>
    lateinit var vServerInfo: Unit

    //to observe loading times
    private val _connectionCompleted = MutableLiveData(false)
    val connectionCompleted: LiveData<Boolean>
        get() = _connectionCompleted

    private val _getGlobalDataCompleted = MutableLiveData(false)
    val getGlobalDataCompleted: LiveData<Boolean>
        get() = _getGlobalDataCompleted

    fun insert(login: Login) {
        viewModelScope.launch {
            repository.insert(login)
            setConnectComplete()
        }
    }

    fun updateLogin(login: Login) {
        viewModelScope.launch {
            repository.update(login)
            setConnectComplete()
        }
    }

    var removeLogin: (Login) -> Unit = {
        viewModelScope.launch {
            repository.deleteLogin(it)
            setConnectComplete()
        }
    }

    fun setConnectComplete() {
        _connectionCompleted.postValue(true)
    }

    fun unsetConnectComplete() {
        _connectionCompleted.value = false
    }

    fun setGetDataComplete() {
        _getGlobalDataCompleted.value = true
    }

    fun unsetGetDataComplete() {
        _connectionCompleted.value = false
    }

    val ts3ApiConnect: (Login) -> Unit = {
        viewModelScope.launch(Dispatchers.IO) {
            repository.apiConnectTelnet(it)
            setConnectComplete()
        }
    }

    fun getGlobalInfo() {
        viewModelScope.launch {
            try {
                hostInfo = repository.apiGetGlobalData()!!
                instanceInfo = repository.apiGetInstanceData()!!
                setGetDataComplete()
            } catch (e: Exception) {
                Log.e(TAG, "Error while getting hostInfo or instanceInfo: $e")
            }
        }
    }

    fun getVirtualServerList() {
        try {
            vServerList = repository.apiGetVServerList()!!
        } catch (e: Exception) {
            Log.e(TAG, "Error while getting vServerList: $e")
        }
    }

    fun getVirtualServerInfo(port: Int) {
        try {
            val vs = vServerList.find { it.port == port }

            vServerInfo = vs?.let { repository.apiConnectVirtualServer(it) }!!
        } catch (e: Exception) {
            Log.e(TAG, "Error while getting vServerList: $e")
            null
        }
    }
}