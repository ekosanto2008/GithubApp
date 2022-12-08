package com.takeshi.gouda.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takeshi.gouda.R
import com.takeshi.gouda.data.User
import com.takeshi.gouda.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private var userLiveData = MutableLiveData<List<User>>()

    fun getUsers() {
        val token = R.string.token
        val users = ApiConfig.getApiService().getTopUsers(token)
        users.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.body() != null) {
                    userLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("TAG", t.message.toString())
            }

        })
    }
    fun observeUserLiveData() : LiveData<List<User>> {
        return userLiveData
    }
}