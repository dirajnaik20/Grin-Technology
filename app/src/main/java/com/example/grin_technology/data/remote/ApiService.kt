package com.example.grin_technology.data.remote

import com.example.grin_technology.data.remote.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL_WALLPAPER = "https://reqres.in/api/users"
    }
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int, @Query("per_page") perPage: Int): Response<UserResponse>
}
