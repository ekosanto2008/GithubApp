package com.takeshi.gouda.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.takeshi.gouda.repository.UserRepository

class FollowersViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getFollowers(
        login: String
    ) = userRepository.getFollowers(login)
}