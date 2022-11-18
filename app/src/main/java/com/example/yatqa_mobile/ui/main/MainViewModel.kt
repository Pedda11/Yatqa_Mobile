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
import com.github.theholywaffle.teamspeak3.api.ServerInstanceProperty
import com.github.theholywaffle.teamspeak3.api.wrapper.HostInfo
import com.github.theholywaffle.teamspeak3.api.wrapper.InstanceInfo
import com.github.theholywaffle.teamspeak3.api.wrapper.VirtualServer
import com.github.theholywaffle.teamspeak3.api.wrapper.VirtualServerInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    //Formats
    val bigIntFormat = NumberFormat.getInstance(Locale.GERMAN)

    //set the variables for database and repository
    private val database = getDatabase(application)
    private val repository = Repository(database)

    val loginList = repository.loginList
    lateinit var hostInfo: HostInfo
    lateinit var instanceInfo: InstanceInfo
    lateinit var vServerList: MutableList<VirtualServer>

    //to observe loading times
    private val _connectionCompleted = MutableLiveData(false)
    val connectionCompleted: LiveData<Boolean>
        get() = _connectionCompleted

    private val _getGlobalDataCompleted = MutableLiveData(false)
    val getGlobalDataCompleted: LiveData<Boolean>
        get() = _getGlobalDataCompleted

    private val _vServerDetails = MutableLiveData<VirtualServer>()
    val vServerDetails: LiveData<VirtualServer>
        get() = _vServerDetails

    private val _vServerInfo = MutableLiveData<VirtualServerInfo?>()
    val vServerInfo: LiveData<VirtualServerInfo?>
        get() = _vServerInfo

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
        viewModelScope.launch {
            try {
                vServerList = repository.apiGetVServerList()!!
            } catch (e: Exception) {
                Log.e(TAG, "Error while getting vServerList: $e")
            }
        }
    }

    fun getCurrentVirtualServerDetails(port: Int) {
        viewModelScope.launch {
            try {
                _vServerDetails.value = vServerList.find { it.port == port }
            } catch (e: Exception) {
                Log.e(TAG, "Error while getting vServerList: $e")
            }
        }
    }

    fun connectToVirtualServer() {
        viewModelScope.launch {
            try {
                repository.apiConnectVirtualServer(_vServerDetails.value!!)
            } catch (e: Exception) {
                Log.e(TAG, "Error while connecting to vServer: $e")
            }
        }
    }

    fun getVirtualServerInfo() {
        _vServerInfo.value = repository.apiGetVirtualServerInfo()
    }

    fun setServerInstanceProperty(property: ServerInstanceProperty, value: String) {
        repository.setServerInstanceProperties(property, value)
    }


}