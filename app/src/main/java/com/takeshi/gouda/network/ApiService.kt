package com.takeshi.gouda.network

import com.takeshi.gouda.model.DetailUser
import com.takeshi.gouda.model.Search
import com.takeshi.gouda.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getTopUsers(@Header("Authentication") token: String): List<User>

    @GET("users/{login}")
    suspend fun getDetailUser(@Path("login") login: String): DetailUser

    @GET("users/{login}/followers")
    suspend fun getFollowers(@Path("login") login: String): List<User>

    @GET("users/{login}/following")
    suspend fun getFollowing(@Path("login") login: String): List<User>

    @GET("search/users")
    suspend fun search(@Query("q") query: String): Search
}