package com.example.matechatting.tcpprocess.repository

import com.example.matechatting.MyApplication
import com.example.matechatting.bean.ChattingBean
import com.example.matechatting.bean.UserBean
import com.example.matechatting.database.AppDatabase
import com.example.matechatting.network.GetOnlineStateService
import com.example.matechatting.network.GetUserByIdService
import com.example.matechatting.network.IdeaApi
import com.example.matechatting.utils.PinyinUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object TCPRepository {

    fun getLoginStateById(id: Int,callback: (Boolean) -> Unit){
        IdeaApi.getApiService(GetOnlineStateService::class.java).updateDirection(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback(it.online)
            },{})
    }

    fun getUserInfo(id: Int, callback: (Boolean) -> Unit) {
        AppDatabase.getInstance(MyApplication.getContext()).userInfoDao().getUserInfo(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback(true)
            }, {
                callback(false)
            })
    }

    fun getAndSaveFriendInfo(state: Int, id: Int, callback: () -> Unit) {
        IdeaApi.getApiService(GetUserByIdService::class.java).getUser(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val info = setInfo(it)
                info.state = state
                saveNewFriendInDB(info, callback)
            }, {})
    }

    fun changeUserState(state: Int, id: Int, callback: () -> Unit) {
        AppDatabase.getInstance(MyApplication.getContext()).userInfoDao().updateStateById(state, id)
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
            sb.append("年入学")
            graduation = sb.toString()
        }
        return userBean
    }

    fun saveNewFriendInDB(userBean: UserBean, callback: () -> Unit) {
        userBean.pinyin = PinyinUtil.getFirstHeadWordChar(userBean.name)
        AppDatabase.getInstance(MyApplication.getContext()).userInfoDao().insertUserInfo(userBean)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback()
            }, {})
    }

    fun saveMessage(chattingBean: ChattingBean, callback: () -> Unit) {
        AppDatabase.getInstance(MyApplication.getContext()).chattingDao().insertChattingBean(chattingBean)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback()
            }, {})
    }

    fun updateOnLineState(state: Boolean, id: Int, callback: () -> Unit = {}) {
        AppDatabase.getInstance(MyApplication.getContext()).userInfoDao().updateOnLineState(state, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback()
            }, {})
    }

    fun getAllIdFromDB(callback: (List<Int>) -> Unit) {
        AppDatabase.getInstance(MyApplication.getContext()).userInfoDao().getAllFriendId()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback(it)
            }, {})
    }
}