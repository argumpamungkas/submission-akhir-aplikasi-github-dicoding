package com.argumelar.aplikasigithubusernavigationdanapi.ui.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.argumelar.aplikasigithubusernavigationdanapi.model.ItemUsers
import com.argumelar.aplikasigithubusernavigationdanapi.network.Repository
import com.argumelar.aplikasigithubusernavigationdanapi.ui.detail.DetailViewModel
import kotlinx.coroutines.launch
import org.koin.dsl.module

val moduleFollowingViewModel = module {
    factory { FollowingViewModel(get()) }
}

class FollowingViewModel(
    private val repository: Repository
) : ViewModel() {


    private val _following = MutableLiveData<List<ItemUsers>>()
    val following: LiveData<List<ItemUsers>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _messageFollowing = MutableLiveData<String>()
    val messageFollowing: LiveData<String> = _messageFollowing

    private val _notFollowing = MutableLiveData<Boolean>()
    val notFollowing: LiveData<Boolean> = _notFollowing

    init {
        getFollowing()
    }

    private fun getFollowing() {
        _isLoading.value = true
        _notFollowing.value = false
        viewModelScope.launch {
            try {
                val response = repository.fetchFollowing(DetailViewModel.USERNAME)
                if (response.isNotEmpty()) {
                    _following.value = response
                    _isLoading.value = false
                } else {
                    _messageFollowing.value = "Tidak mempunyai following"
                    _notFollowing.value = true
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _messageFollowing.value = "Error ${e.message}"
                _notFollowing.value = true
            }
        }
    }


}
