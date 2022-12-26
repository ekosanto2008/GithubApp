package com.takeshi.gouda.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takeshi.gouda.model.Search
import com.takeshi.gouda.model.User
import com.takeshi.gouda.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {
    private var userLiveData = MutableLiveData<List<User>>()

    fun searchUser(query: String) {
        val data = ApiConfig.getApiService().search(query)
        data.enqueue(object : Callback<Search> {
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                if (response.body() != null) {
                    userLiveData.value = response.body()?.items
                }
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                Log.d("TAG", t.message.toString())
            }

        })
    }
    fun observeUserLiveData(): LiveData<List<User>> {
        return userLiveData
    }
}