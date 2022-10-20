package com.example.yatqa_mobile.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.yatqa_mobile.data.Repository
import com.example.yatqa_mobile.data.datamodels.Login
import com.example.yatqa_mobile.data.local.getDatabase
import kotlinx.coroutines.launch
import kotlin.math.log

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
    fun insert(login: Login){
        viewModelScope.launch {
            repository.insert(login)
            _actionCompleted.value = true
        }
    }

    fun updateLogin(login: Login){
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
}