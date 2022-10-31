package com.example.yatqa_mobile.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.yatqa_mobile.data.Repository
import com.example.yatqa_mobile.data.datamodels.Login
import com.example.yatqa_mobile.data.local.getDatabase
import com.github.theholywaffle.teamspeak3.TS3Config
import com.github.theholywaffle.teamspeak3.TS3Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log
import kotlin.random.Random

class MainViewModel(application: Application) : AndroidViewModel(application) {

    //set the variables for database and repository
    private val database = getDatabase(application)
    private val repository = Repository(database)

    val loginList = repository.loginList

    //to observe loading times
    private val _actionCompleted = MutableLiveData(false)
    val actionCompleted: LiveData<Boolean>
        get() = _actionCompleted

    //insert a new login to database
    fun insert(login: Login) {
        viewModelScope.launch {
            repository.insert(login)
            _actionCompleted.value = true
        }
    }

    fun updateLogin(login: Login) {
        viewModelScope.launch {
            repository.update(login)
            _actionCompleted.value = true
        }
    }

    fun deleteContact(login: Login) {
        viewModelScope.launch {
            repository.deleteLogin(login)
            _actionCompleted.value = true
        }
    }

    fun unsetComplete() {
        _actionCompleted.value = false
    }

    val ts3ApiConnect: (Int) -> Unit = { loginId: Int ->
        viewModelScope.launch(Dispatchers.IO) {

            val login = loginList.value?.find { it.id == loginId }

            if (login != null) {

                val ts3 = TS3Config()
                ts3.setHost(login.ip)
                ts3.setQueryPort(login.qPort)

                val ts3Query = TS3Query(ts3)
                ts3Query.connect()


                val tsApi = ts3Query.api

                tsApi.login(login.userName, login.userPassword)
                tsApi.selectVirtualServerByPort(8080)
                tsApi.setNickname("API-TEST-BOT ${Random.nextInt(1,50000)}")
                tsApi.sendChannelMessage("Hi there.")

                val dsiacuj = tsApi.hostInfo
                println(dsiacuj)
            }
        }
    }
}