package com.takeshi.gouda.build

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.takeshi.gouda.di.UserInjection
import com.takeshi.gouda.repository.UserRepository
import com.takeshi.gouda.ui.viewmodel.*

class UserViewModelFactory private constructor(private val userRepository: UserRepository): ViewModelProvider.NewInstanceFactory() {

    private fun fail(message: String) : Nothing {
        throw IllegalArgumentException(message)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(FollowersViewModel::class.java) -> {
                FollowersViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(FollowingViewModel::class.java) -> {
                FollowingViewModel(userRepository) as T
            }
            else -> {
                fail("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        private var instance: UserViewModelFactory? = null
        fun getInstance(context: Context): UserViewModelFactory = instance ?: synchronized(this) {
            instance ?: UserViewModelFactory(UserInjection.provideRepository())
        }
    }
}