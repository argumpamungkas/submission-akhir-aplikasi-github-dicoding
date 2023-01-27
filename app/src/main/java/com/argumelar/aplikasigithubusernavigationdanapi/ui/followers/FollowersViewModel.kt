package com.argumelar.aplikasigithubusernavigationdanapi.ui.followers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argumelar.aplikasigithubusernavigationdanapi.model.ItemUsers
import com.argumelar.aplikasigithubusernavigationdanapi.network.Repository
import com.argumelar.aplikasigithubusernavigationdanapi.ui.detail.DetailViewModel
import kotlinx.coroutines.launch
import org.koin.dsl.module

val moduleFollowersViewModel = module {
    factory { FollowersViewModel(get()) }
}

class FollowersViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _followers = MutableLiveData<List<ItemUsers>>()
    val followers: LiveData<List<ItemUsers>> = _followers

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _notFollowers = MutableLiveData<Boolean>()
    val notFollowers: LiveData<Boolean> = _notFollowers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getFollowers()
    }

    private fun getFollowers() {
        _isLoading.value = true
        _notFollowers.value = false
        viewModelScope.launch {
            try {
                val response = repository.fetchFollowers(DetailViewModel.USERNAME)
                if (response.isNotEmpty()) {
                    _followers.value = response
                    _isLoading.value = false
                } else {
                    _message.value = "Tidak mempunyai followers"
                    _notFollowers.value = true
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _message.value = "Error ${e.message}"
                _notFollowers.value = true
            }
        }
    }

}