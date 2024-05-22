package com.example.grin_technology.presentation

import android.util.Log
import androidx.lifecycle.*
import com.example.grin_technology.data.remote.model.User
import com.example.grin_technology.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var currentPage = 1

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = repository.getUsers(currentPage, 5)
            if (response.isSuccessful) {
                Log.d("Diraj", "Fetching page $currentPage with page size ${response.body()?.perPage}")
                Log.d("Diraj", "Fetching page $currentPage with list size ${response.body()?.data?.size}")
                response.body()?.data?.let {
                    Log.d("Diraj", "Fetching page $currentPage with list size ${it.size}")
                    _users.value = (_users.value ?: emptyList()) + it
                }
            }
            _isLoading.value = false
        }
    }

    fun loadNextPage() {
        currentPage++
        fetchUsers()
    }
}
