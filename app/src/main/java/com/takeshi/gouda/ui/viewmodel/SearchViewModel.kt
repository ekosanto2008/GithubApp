package com.takeshi.gouda.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.takeshi.gouda.repository.UserRepository

class SearchViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getSearch(
        login: String
    ) = userRepository.getSearch(login)
}