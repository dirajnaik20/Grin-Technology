package com.example.grin_technology.repository

import com.example.grin_technology.data.remote.ApiService
import javax.inject.Inject
class UserRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getUsers(page: Int, perPage: Int) = apiService.getUsers(page, perPage)
}
