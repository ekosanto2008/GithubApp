package com.takeshi.gouda.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takeshi.gouda.model.DetailUser
import com.takeshi.gouda.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private var userLiveData = MutableLiveData<DetailUser>()

    fun getDetailUser(username: String) {
        val user = ApiConfig.getApiService().getDetailUser(username)
        user.enqueue(object : Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                if (response.body() != null) {
                    userLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                Log.d("TAG", t.message.toString())
            }

        })
    }
    fun observeUserLiveData(): LiveData<DetailUser> {
        return userLiveData
    }
}