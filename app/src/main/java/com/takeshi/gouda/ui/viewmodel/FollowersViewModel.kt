package com.takeshi.gouda.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takeshi.gouda.model.User
import com.takeshi.gouda.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {
    private var userLiveData = MutableLiveData<List<User>>()

    fun getFollowers(login: String) {
        val user = ApiConfig.getApiService().getFollowers(login)
        user.enqueue(object : Callback<List<User>> {
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
    fun observeUserLiveData(): LiveData<List<User>> {
        return userLiveData
    }
}