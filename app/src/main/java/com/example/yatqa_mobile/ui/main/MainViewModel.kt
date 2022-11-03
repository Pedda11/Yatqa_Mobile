package com.example.yatqa_mobile.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.yatqa_mobile.data.Repository
import com.example.yatqa_mobile.data.datamodels.Login
import com.example.yatqa_mobile.data.local.getDatabase
import com.github.theholywaffle.teamspeak3.api.wrapper.HostInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    //set the variables for database and repository
    private val database = getDatabase(application)
    private val repository = Repository(database)

    val loginList = repository.loginList
    lateinit var hostInfo: HostInfo

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

    fun getHostInfo() {
        viewModelScope.launch {
            hostInfo = repository.apiGetGlobalData()!!
            setGetDataComplete()
        }
    }

    val ts3ApiConnect: (Login) -> Unit = {
        viewModelScope.launch(Dispatchers.IO) {
            repository.apiConnect(it)
            setConnectComplete()
        }
    }
}