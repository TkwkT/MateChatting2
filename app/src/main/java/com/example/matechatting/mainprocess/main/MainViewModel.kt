package com.example.matechatting.mainprocess.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.matechatting.bean.BigDirectionBean
import com.example.matechatting.mainprocess.direction.DirectionActivityRepository
import com.example.matechatting.mainprocess.direction.DirectionFragmentRepository

class MainViewModel(private val repository: MainRepository,private val directionActivityRepository:DirectionActivityRepository,private val directionFragmentRepository:DirectionFragmentRepository) : ViewModel() {

    fun getFriend(callback: () -> Unit) {
        repository.getAllFriendIdFromNet(callback)
    }

    fun getMineInfo(){
        Log.d("aaa","main getMineInfo")
        repository.getMineFromNet()
    }

    fun getDirection(){
        directionActivityRepository.getBigDirectionFromDB {
            if (it.isNullOrEmpty()){
                getBigDirection {big->
                    for (bean:BigDirectionBean in big){
                        getSmallDirection(bean.id)
                    }
                }
            }
        }
    }

    private fun getBigDirection(callback: (List<BigDirectionBean>) -> Unit){
        directionActivityRepository.getBigDirectionFromNet {
            callback(it)
        }
    }

    private fun getSmallDirection(big:Int){
        directionFragmentRepository.getSmallDirection(big)
    }

}