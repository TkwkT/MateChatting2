package com.example.matechatting.function.direction

import com.example.matechatting.base.BaseRepository
import com.example.matechatting.bean.BigDirectionBean
import com.example.matechatting.network.GetBigDirectionService
import com.example.matechatting.network.IdeaApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DirectionActivityRepository : BaseRepository {

    fun getBigDirection(callback: (List<BigDirectionBean>) -> Unit) {
        IdeaApi.getApiService(GetBigDirectionService::class.java).getBigDirection()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val list = changePosition(it)
                callback(list)
            }, {})
    }

    private fun changePosition(list: List<BigDirectionBean>): List<BigDirectionBean> {
        val array = ArrayList<BigDirectionBean>()
        if (list.isNullOrEmpty()) {
            return array
        }
        array.add(list[8])
        array.add(list[9])
        array.add(list[14])
        for ((i, b: BigDirectionBean) in list.withIndex()) {
            if (i == 8 || i == 9 || i == 14) {
                continue
            } else {
                array.add(b)
            }
        }
        return array
    }

}