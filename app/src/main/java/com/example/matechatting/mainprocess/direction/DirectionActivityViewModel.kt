package com.example.matechatting.mainprocess.direction

import androidx.lifecycle.ViewModel
import com.example.matechatting.bean.BigDirectionBean

class DirectionActivityViewModel(private val repository: DirectionActivityRepository) : ViewModel() {

    fun getBigDirection(callback: (List<BigDirectionBean>) -> Unit) {
        repository.getBigDirection(callback)
    }
}