package com.takeshi.gouda.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.takeshi.gouda.repository.UserRepository

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getDetailUser(
        login: String
    ) = userRepository.getDetailUser(login)
}