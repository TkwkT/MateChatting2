package com.example.matechatting.mainprocess.main

import android.util.Log
import com.example.matechatting.bean.UserBean
import com.example.matechatting.database.UserInfoDao
import com.example.matechatting.network.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class MainRepository(private val userInfoDao: UserInfoDao) {

    fun getAllFriendIdFromNet(callback: () -> Unit) {
        IdeaApi.getApiService(GetAllFriendIdService::class.java).getAllFriendId()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ net ->
                Log.d("aaa", "数据库查询 $net")
                val array = ArrayList<Int>()
                array.addAll(net.payload)
                getIdFromDB { db ->
                    array.removeAll(db)
                    Log.d("aaa", "数据库查询 $array")
                    if (array.isNotEmpty() && array.size < 5) {
                        for (id: Int in array) {
                            getUserById(id)
                        }
                    } else if (array.size >= 5) {
                        getAllFriendFromNet(callback)
                    } else {
                    }
                }
            }, {})
    }

    fun getIdFromDB(callback: (List<Int>) -> Unit) {
        userInfoDao.getAllFriendId()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback(it)
                Log.d("aaa", "数据库查询 $it")
            }, {
                val array = ArrayList<Int>()
                callback(array)
            })
    }

    fun getUserById(id: Int, callback: () -> Unit = {}) {
        IdeaApi.getApiService(GetUserByIdService::class.java).getUser(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val bean = setInfo(it)
                updateState(bean, 4)
            }, {})
    }

    fun getAllFriendFromNet(callback: () -> Unit) {
        IdeaApi.getApiService(GetAllFriendService::class.java).getAllFriend()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                for (bean: UserBean in it) {
                    if (bean != null) {
                        val temp = setInfo(bean)
//                        bean.pinyin = PinyinUtil.getPinyin(bean.name)
                        updateState(temp, 4)
                    }
                }
                callback()
            }, {})
    }

    fun updateState(userBean: UserBean, state: Int, callback: () -> Unit = {}) {
        userBean.state = state
        userInfoDao.insertUserInfo(userBean)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback()
            }, {})
    }

    private fun setInfo(userBean: UserBean): UserBean {
        userBean.apply {
            if (!directions.isNullOrEmpty()) {
                val sb = StringBuilder()
                for (s: String in directions!!) {
                    sb.append(" ")
                    sb.append(s)
                }
                direction = sb.toString()
            }
            val sb = StringBuilder()
            sb.append(graduationYear)
            sb.append("级毕业")
            graduation = sb.toString()
        }
        return userBean
    }

    companion object {
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance(userInfoDao: UserInfoDao) =
            instance ?: synchronized(this) {
                instance ?: MainRepository(userInfoDao).also { instance = it }
            }
    }
}