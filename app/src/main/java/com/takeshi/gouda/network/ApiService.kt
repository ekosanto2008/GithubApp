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
    fun getTopUsers(@Header("Authentication") token: Int): Call<List<User>>

    @GET("users/{login}")
    fun getDetailUser(@Path("login") login: String): Call<DetailUser>

    @GET("users/{login}/followers")
    fun getFollowers(@Path("login") login: String): Call<List<User>>

    @GET("users/{login}/following")
    fun getFollowing(@Path("login") login: String): Call<List<User>>

    @GET("search/users")
    fun search(@Query("q") query: String): Call<Search>
}