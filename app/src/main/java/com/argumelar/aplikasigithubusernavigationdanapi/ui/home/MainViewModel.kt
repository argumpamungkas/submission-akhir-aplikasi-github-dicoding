package com.argumelar.aplikasigithubusernavigationdanapi.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.argumelar.aplikasigithubusernavigationdanapi.model.UsersResponse
import com.argumelar.aplikasigithubusernavigationdanapi.network.Repository
import com.argumelar.aplikasigithubusernavigationdanapi.persistence.PreferenceHelper
import kotlinx.coroutines.launch
import org.koin.dsl.module

val moduleMainViewModel = module {
    factory { MainViewModel(get(), get()) }
}

class MainViewModel(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    private val sharedPref = PreferenceHelper(getApplication())

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _listUser = MutableLiveData<UsersResponse>()
    val listUser: LiveData<UsersResponse> = _listUser

    private val _notUsers = MutableLiveData<Boolean>()
    val notUsers: LiveData<Boolean> = _notUsers

    private val _tema = MutableLiveData<Int>()
    val tema: LiveData<Int> = _tema

    fun searchUser(username: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.fetchSearchUser(username)
                if (response.total_count != 0) {
                    _listUser.value = response
                    _notUsers.value = false
                } else {
                    _notUsers.value = true
                    _message.value = "User tidak ditemukan"
                }
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                _message.value = "Error ${e.message}"
                _notUsers.value = true
            }
        }
    }

    fun optionTheme() {
        viewModelScope.launch {
            if (_tema.value == 0){
                _tema.value = 1
                sharedPref.putTheme(PreferenceHelper.DARK_MODE, 1)
            } else {
                _tema.value = 0
                sharedPref.clear()
            }
        }
    }

    fun checkTheme(){
        _tema.value = sharedPref.getTheme(PreferenceHelper.DARK_MODE)
    }
}