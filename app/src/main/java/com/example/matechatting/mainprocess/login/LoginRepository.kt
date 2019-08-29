package com.example.matechatting.mainprocess.login

import android.util.Log
import com.example.matechatting.MyApplication
import com.example.matechatting.base.BaseRepository
import com.example.matechatting.bean.AccountBean
import com.example.matechatting.database.LoginDao
import com.example.matechatting.mainprocess.login.LoginState.Companion.ERROR
import com.example.matechatting.mainprocess.login.LoginState.Companion.FIRST
import com.example.matechatting.mainprocess.login.LoginState.Companion.NOT_FIRST
import com.example.matechatting.mainprocess.login.LoginState.Companion.NO_NETWORK
import com.example.matechatting.network.IdeaApi
import com.example.matechatting.network.LoginService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginRepository(private val loginDao: LoginDao) : BaseRepository {

    fun checkFromDatabase(account: String, password: String, callback: (state: Int, List<String>) -> Unit) {
        loginDao.checkAccount(account)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (password == it.password) {
                    saveState(account, it.token, it.id, it.inSchool)
                    callback(NOT_FIRST, arrayListOf(""))
                } else if (password != it.password) {
                    callback(ERROR, arrayListOf(""))
                }
            }, {
                callback(NO_NETWORK, arrayListOf(""))
            })
    }

    fun checkFromNetwork(
        account: String,
        password: String,
        callback: (state: Int, List<String>) -> Unit
    ) {
        IdeaApi.getApiService(LoginService::class.java, false).getLoginAndGetToken(account, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("aaa", "checkFromNetwork : $it")
                val payload = it.payload
                saveInDB(account, password, payload.token, payload.id, payload.inSchool)
                Log.d("aaa", "payload.first" + payload.first)
                if (payload.first) {
                    callback(FIRST, arrayListOf(payload.token, payload.inSchool.toString()))
                } else {
                    saveState(account, payload.token, payload.id, payload.inSchool)
                    callback(NOT_FIRST, arrayListOf(""))
                }
            }, {
                callback(ERROR, arrayListOf(""))
            })
    }

    private fun saveState(account: String, token: String, id: Int, inSchool: Boolean) {
        Log.d("aaa", "id$id")
        MyApplication.saveLoginState(account, token, id, inSchool)
    }

    private fun saveInDB(account: String, password: String, token: String, id: Int, inSchool: Boolean) {
        val accountBean = AccountBean(account, password, token, true, id, inSchool)
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