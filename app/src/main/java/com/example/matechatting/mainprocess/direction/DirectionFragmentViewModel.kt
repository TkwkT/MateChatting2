package com.example.matechatting.mainprocess.direction

import androidx.lifecycle.ViewModel
import com.example.matechatting.bean.SaveDirectionBean

class DirectionFragmentViewModel(private val repository: DirectionFragmentRepository) : ViewModel() {

    fun getSmallDirection(id: Int, callback: (SaveDirectionBean) -> Unit) {
        repository.getSmallDirection(id,callback)
    }
}