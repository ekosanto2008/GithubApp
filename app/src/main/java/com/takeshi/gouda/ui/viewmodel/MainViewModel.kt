package com.takeshi.gouda.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.takeshi.gouda.repository.UserRepository

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUsers() = userRepository.getUser()
}