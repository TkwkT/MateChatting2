package com.example.matechatting.mainprocess.mine

import android.util.Log
import com.bumptech.glide.Glide
import com.example.matechatting.MyApplication
import com.example.matechatting.network.GetMineService
import com.example.matechatting.network.IdeaApi
import com.example.matechatting.base.BaseRepository
import com.example.matechatting.bean.UserBean
import com.example.matechatting.database.UserInfoDao
import com.example.matechatting.utils.NetworkState
import com.example.matechatting.utils.isNetworkConnected
import com.example.matechatting.utils.runOnNewThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MineRepository(private val userInfoDao: UserInfoDao) : BaseRepository {

    fun getMine(callback: (UserBean) -> Unit) {
        if (isNetworkConnected(MyApplication.getContext()) == NetworkState.NONE) {
            getMineFromDB(callback)
        } else {
            getMineFromNet(callback)
        }
    }

    fun getMineFromNet(callback: (UserBean) -> Unit) {
        IdeaApi.getApiService(GetMineService::class.java).getMine()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                val info = setInfo(it)
                info.state = 1
                Log.d("aaa", "getMineFromNet" + info)
                callback(info)
                saveInDB(info)
            }
            .subscribe()
    }

    fun saveHeadImagePath(url: String) {
        if (MyApplication.getUserId() == null) {
            return
        }
        runOnNewThread {
            val target = Glide.with(MyApplication.getContext())
                .asFile()
                .load(url)
                .submit()
            val path = target.get().absolutePath
            Log.d("aaa", "saveHeadImagePath $path")
            userInfoDao.updateHeadImage(path, MyApplication.getUserId()!!)
        }
    }

    private fun saveInDB(userBean: UserBean) {
        runOnNewThread {
            userInfoDao.insertUserInfo(userBean)
        }
    }

    fun getMineFromDB(callback: (UserBean) -> Unit) {
        MyApplication.getUserId()?.let {
            userInfoDao.getUserInfo(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({user->
                    callback(user)
                }, {})
        }
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
            sb.append("年入学")
            graduation = sb.toString()
        }
        return userBean
    }

    companion object {
        @Volatile
        private var instance: MineRepository? = null

        fun getInstance(userInfoDao: UserInfoDao) =
            instance ?: synchronized(this) {
                instance ?: MineRepository(userInfoDao).also { instance = it }
            }
    }
}