package com.takeshi.gouda.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.takeshi.gouda.model.DetailUser
import com.takeshi.gouda.model.Search
import com.takeshi.gouda.model.User
import com.takeshi.gouda.network.ApiService

class UserRepository(private val apiService: ApiService) {

    fun getUser() : LiveData<com.takeshi.gouda.Result<List<User>>> = liveData {
        emit(com.takeshi.gouda.Result.Loading)
        try {
            val result = apiService.getTopUsers("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")
            emit(com.takeshi.gouda.Result.Success(result))
        }  catch (e: Exception) {
            Log.d("User Repository","Get Top User: ${e.message.toString()}")
            emit(com.takeshi.gouda.Result.Error(e.message.toString()))
        }
    }

    fun getDetailUser(username: String) : LiveData<com.takeshi.gouda.Result<DetailUser>> = liveData {
        emit(com.takeshi.gouda.Result.Loading)
        try {
            val result = apiService.getDetailUser(username)
            emit(com.takeshi.gouda.Result.Success(result))
        } catch (e: Exception) {
            Log.d("User Repository","Get Detail User: ${e.message.toString()}")
            emit(com.takeshi.gouda.Result.Error(e.message.toString()))
        }
    }

    fun getFollowers(login: String) : LiveData<com.takeshi.gouda.Result<List<User>>> = liveData {
        emit(com.takeshi.gouda.Result.Loading)
        try {
            val result = apiService.getFollowers(login)
            emit(com.takeshi.gouda.Result.Success(result))
        }  catch (e: Exception) {
            Log.d("User Repository","Get Followers: ${e.message.toString()}")
            emit(com.takeshi.gouda.Result.Error(e.message.toString()))
        }
    }

    fun getFollowing(login: String) : LiveData<com.takeshi.gouda.Result<List<User>>> = liveData {
        emit(com.takeshi.gouda.Result.Loading)
        try {
            val result = apiService.getFollowing(login)
            emit(com.takeshi.gouda.Result.Success(result))
        }  catch (e: Exception) {
            Log.d("User Repository","Get Following: ${e.message.toString()}")
            emit(com.takeshi.gouda.Result.Error(e.message.toString()))
        }
    }

    fun getSearch(login: String) : LiveData<com.takeshi.gouda.Result<Search>> = liveData {
        emit(com.takeshi.gouda.Result.Loading)
        try {
            val result = apiService.search(login)
            emit(com.takeshi.gouda.Result.Success(result))
        }  catch (e: Exception) {
            Log.d("User Repository","Search User: ${e.message.toString()}")
            emit(com.takeshi.gouda.Result.Error(e.message.toString()))
        }
    }

    companion object {
        private var instance: UserRepository? = null
        fun getInstance(apiService: ApiService): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(apiService)
        }.also { instance = it }
    }
}