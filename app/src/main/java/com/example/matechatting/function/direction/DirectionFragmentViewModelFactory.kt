package com.example.matechatting.function.direction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DirectionFragmentViewModelFactory(private val repository: DirectionFragmentRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DirectionFragmentViewModel(repository) as T
    }
}