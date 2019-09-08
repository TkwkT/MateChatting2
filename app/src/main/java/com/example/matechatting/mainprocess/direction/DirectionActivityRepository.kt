package com.example.matechatting.mainprocess.direction

import android.util.Log
import com.example.matechatting.base.BaseRepository
import com.example.matechatting.bean.BigDirectionBean
import com.example.matechatting.bean.DirectionBean
import com.example.matechatting.bean.PostDirectionBean
import com.example.matechatting.database.DirectionDao
import com.example.matechatting.network.*
import com.example.matechatting.utils.runOnNewThread
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DirectionActivityRepository(private val directionDao: DirectionDao) : BaseRepository {

    fun saveDirection(postDirectionBean: PostDirectionBean, token: String, callback: () -> Unit) {
        val gson = Gson()
        val array = gson.toJson(postDirectionBean.directions)
        val service: UpdateDirectionService = if (token.isEmpty()) {
            IdeaApi.getApiService(UpdateDirectionService::class.java)
        } else {
            IdeaApi.getApiService(UpdateDirectionService::class.java, false, OtherTokenInterceptor(token))
        }
        service.updateDirection(array)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.success) {
                    callback()
                }
            }, {})
    }

    fun updateDirectionState(isSelect: Boolean, id: Int, callback: () -> Unit = {}) {
        directionDao.updateState(isSelect, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("bbb","id 选中 $id")
                callback()
            }, {})
    }

    fun getBigDirectionFromDB(callback: (List<BigDirectionBean>) -> Unit) {
        directionDao.selectDirectionByParent(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                Log.d("aaa", "大方向 $it")
                if (!it.isNullOrEmpty()) {
                    val bigArray = directionToBig(it)
                    val temp = changePosition(bigArray)
                    callback(temp)
                } else {
                    val array = ArrayList<BigDirectionBean>()
                    callback(array)
                }
            }
            .doOnError {
                val array = ArrayList<BigDirectionBean>()
                callback(array)
            }
            .subscribe()
    }

    private fun directionToBig(list: List<DirectionBean>): List<BigDirectionBean> {
        val bigArray = ArrayList<BigDirectionBean>()
        for (bean: DirectionBean in list) {
            bean.apply {
                val big = BigDirectionBean(directionName, id, isSelect)
                bigArray.add(big)
            }
        }
        return bigArray
    }


    fun getBigDirectionFromNet(callback: (List<BigDirectionBean>) -> Unit) {
        IdeaApi.getApiService(GetBigDirectionService::class.java, false).getBigDirection()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val list = changePosition(it)
                saveBigDirection(list)
                callback(list)
            }, {})
    }

    fun saveBigDirection(list: List<BigDirectionBean>) {
        for (bean: BigDirectionBean in list) {
            bean.apply {
                val directionBean = DirectionBean(id, directionName,0)
                insertBigDirection(directionBean)
            }
        }
    }

    private fun insertBigDirection(directionBean: DirectionBean) {
        Log.d("aaa", "插入的大方向 $directionBean")
        directionDao.insertDirection(directionBean)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("aaa", "大标签插入成功 $it")
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

    companion object {
        @Volatile
        private var instance: DirectionActivityRepository? = null

        fun getInstance(directionDao: DirectionDao) =
            instance ?: synchronized(this) {
                instance ?: DirectionActivityRepository(directionDao).also { instance = it }
            }
    }

}