package com.example.matechatting.mainprocess.main

import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    fun getFriend(callback: () -> Unit) {
        repository.getAllFriendIdFromNet(callback)
    }

    fun getMineInfo(){
        Log.d("aaa","main getMineInfo")
        repository.getMineFromNet()
    }

}