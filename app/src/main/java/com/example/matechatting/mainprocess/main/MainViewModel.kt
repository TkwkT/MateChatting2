package com.example.matechatting.mainprocess.main

import androidx.lifecycle.ViewModel

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    fun getFriend(callback: () -> Unit) {
        repository.getAllFriendIdFromNet(callback)
    }

}