package com.example.grin_technology.data.remote.model


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val `data`: List<User>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)