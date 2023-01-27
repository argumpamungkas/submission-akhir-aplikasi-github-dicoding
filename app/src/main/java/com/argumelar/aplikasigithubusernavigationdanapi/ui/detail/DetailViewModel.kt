package com.argumelar.aplikasigithubusernavigationdanapi.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argumelar.aplikasigithubusernavigationdanapi.network.Repository
import com.argumelar.aplikasigithubusernavigationdanapi.model.UserDetailResponse
import kotlinx.coroutines.launch
import org.koin.dsl.module

val moduleDetailViewModel = module {
    factory { DetailViewModel(get()) }
}

class DetailViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _detailUser = MutableLiveData<UserDetailResponse>()
    val detailUser: LiveData<UserDetailResponse> = _detailUser

    private val _message = MutableLiveData<String>()

    val isFavorite by lazy { MutableLiveData(0) }

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        getDetailUser()
    }

    private fun getDetailUser() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.fetchDetailUser(USERNAME)
                _detailUser.value = response
                _loading.value = false
            } catch (e: Exception){
                _message.value = "Error ${e.message}"
                _loading.value = false
            }
        }
    }

    fun favorite(user: UserDetailResponse){
        viewModelScope.launch {
            if (isFavorite.value == 0){
                repository.save(user)
            } else {
                repository.remove(user)
            }
            find(user)
        }
    }

    fun find(user: UserDetailResponse) {
        viewModelScope.launch {
            isFavorite.value = repository.find(user)
        }
    }

    companion object {
        var USERNAME = ""
    }

}