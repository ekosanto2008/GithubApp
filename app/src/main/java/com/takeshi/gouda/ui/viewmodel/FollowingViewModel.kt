package com.takeshi.gouda.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.takeshi.gouda.repository.UserRepository

class FollowingViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getFollowing(
        login: String
    ) = userRepository.getFollowing(login)
}