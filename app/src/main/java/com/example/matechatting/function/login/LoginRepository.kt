package com.example.matechatting.function.login

import android.util.Log
import com.example.matechatting.MyApplication
import com.example.matechatting.base.BaseRepository
import com.example.matechatting.bean.AccountBean
import com.example.matechatting.database.LoginDao
import com.example.matechatting.function.login.LoginState.Companion.ERROR
import com.example.matechatting.function.login.LoginState.Companion.FIRST
import com.example.matechatting.function.login.LoginState.Companion.NOT_FIRST
import com.example.matechatting.function.login.LoginState.Companion.NO_NETWORK
import com.example.matechatting.network.IdeaApi
import com.example.matechatting.network.LoginService
import com.example.matechatting.utils.runOnNewThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginRepository(private val loginDao: LoginDao) : BaseRepository {

    fun checkFromDatabase(account: String, password: String, callback: (state: Int, token: String) -> Unit) {
        loginDao.checkAccount(account)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (password == it.password) {
                    saveState(account, it.token, it.id)
                    callback(NOT_FIRST, "")
                } else if (password != it.password) {
                    callback(ERROR, "")
                }
            }, {
                callback(NO_NETWORK, "")
            })
    }

    fun checkFromNetwork(
        account: String,
        password: String,
        callback: (state: Int, token: String) -> Unit
    ) {
        IdeaApi.getApiService(LoginService::class.java, false).getLoginAndGetToken(account, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("aaa", "checkFromNetwork : $it")
                val payload = it.payload
                saveInDB(account, password, payload.token, payload.id)
                if (payload.first) {
                    callback(FIRST, payload.token)
                } else {
                    saveState(account, payload.token, payload.id)
                    callback(NOT_FIRST, "")
                }
            }, {
                callback(ERROR, "")
            })
    }

    private fun saveState(account: String, token: String, id: Int) {
        MyApplication.saveLoginState(account, token, id)
    }

    private fun saveInDB(account: String, password: String, token: String, id: Int) {
        val accountBean = AccountBean(account, password, token, true, id)
        loginDao.insertAccount(accountBean)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("aaa", "saveInDB$it")
            }, {})
    }

    companion object {

        @Volatile
        private var instance: LoginRepository? = null

        fun getInstance(classifyDao: LoginDao) =
            instance ?: synchronized(this) {
                instance
                    ?: LoginRepository(classifyDao).also { instance = it }
            }
    }

}

class LoginState {
    companion object {
        //输入账号为空
        const val ACCOUNT_NULL = 1
        //输入密码为空
        const val PASSWORD_NULL = 2
        //账号或密码错误
        const val ERROR = 3
        //请连接网络
        const val NO_NETWORK = 4
        //下两个都为验证成功
        const val FIRST = 5
        const val NOT_FIRST = 6
    }
}