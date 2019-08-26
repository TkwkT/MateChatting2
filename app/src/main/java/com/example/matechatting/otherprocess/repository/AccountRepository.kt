package com.example.matechatting.otherprocess.repository

import com.example.matechatting.database.LoginDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AccountRepository(private val dao: LoginDao) {

    fun getToken(callback: (String) -> Unit) {
        dao.getLoginToken(true)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                callback(it.token)
            }
            .doOnError {
                callback("")
            }
            .subscribe()
    }

    companion object{
        @Volatile
        private var instance: AccountRepository? = null

        fun getInstance(loginDao: LoginDao) =
            instance ?: synchronized(this) {
                instance ?: AccountRepository(loginDao).also { instance = it }
            }
    }
}