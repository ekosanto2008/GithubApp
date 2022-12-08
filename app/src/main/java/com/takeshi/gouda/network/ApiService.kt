package com.takeshi.gouda.network

import com.takeshi.gouda.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("users")
    fun getTopUsers(@Header("Authentication") token: Int): Call<List<User>>
}