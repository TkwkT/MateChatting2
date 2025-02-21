package com.example.matechatting.mainprocess.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.matechatting.mainprocess.direction.DirectionActivityRepository
import com.example.matechatting.mainprocess.direction.DirectionFragmentRepository

class MainViewModelFactory(private val repository: MainRepository, private val directionActivityRepository: DirectionActivityRepository, private val directionFragmentRepository: DirectionFragmentRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository,directionActivityRepository,directionFragmentRepository) as T
    }
}