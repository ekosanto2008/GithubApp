package com.takeshi.gouda.di

import com.takeshi.gouda.network.ApiConfig
import com.takeshi.gouda.repository.UserRepository

object UserInjection {
    fun provideRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }
}