package com.example.matechatting.mainprocess.myinfo

import android.util.Log
import com.bumptech.glide.Glide
import com.example.matechatting.MyApplication
import com.example.matechatting.base.BaseRepository
import com.example.matechatting.bean.PostUserBean
import com.example.matechatting.bean.UserBean
import com.example.matechatting.database.LoginDao
import com.example.matechatting.database.UserInfoDao
import com.example.matechatting.network.GetMyInfoService
import com.example.matechatting.network.IdeaApi
import com.example.matechatting.network.OtherTokenInterceptor
import com.example.matechatting.network.UpdateUserInfoService
import com.example.matechatting.utils.runOnNewThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MyInfoRepository(private val userInfoDao: UserInfoDao, private val loginDao: LoginDao) : BaseRepository {

    fun saveMyInfo(userBean: UserBean, callback: () -> Unit, token: String = "") {
        saveInDB(userBean)
        saveInNet(userBean, callback, token)
    }

    private fun saveInDB(userBean: UserBean) {
        userInfoDao.insertUserInfo(userBean)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("aaa", "保存成功 $it")
            }, {})
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

    private fun saveInNet(userBean: UserBean, callback: () -> Unit = {}, token: String = "") {
        val service: UpdateUserInfoService = if (token.isEmpty()) {
            IdeaApi.getApiService(UpdateUserInfoService::class.java)
        } else {
            IdeaApi.getApiService(UpdateUserInfoService::class.java, false, OtherTokenInterceptor(token))
        }
        service.update(userBeanToPostUserBean(userBean))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("aaa", "token.isNotEmpty()" + token.isNotEmpty().toString())
                if (it.success && token.isNotEmpty()) {
                    Log.d("aaa", "saveInNet$it")
                    doOnSaveSuccess(token, callback)
                }else{
                    callback()
                }
            }, {})
    }

    private fun doOnSaveSuccess(token: String, callback: () -> Unit) {
        loginDao.getAllByToken(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("aaa", "doOnSaveSuccess$it")
                saveState(it.account, it.token, it.id, it.inSchool, callback)
            }, {})
    }

    private fun saveState(account: String, token: String, id: Int, inSchool: Boolean, callback: () -> Unit) {
        MyApplication.saveLoginState(account, token, id, inSchool)
        callback()
    }

    private fun userBeanToPostUserBean(userBean: UserBean): PostUserBean {
        val postUserBean = PostUserBean()
        userBean.apply {
            postUserBean.city = city
            postUserBean.company = company
            postUserBean.email = email
            postUserBean.gender = gender
            postUserBean.graduationYear = graduationYear
            postUserBean.job = job
            postUserBean.majorName = majorName
            postUserBean.qqAccount = qqAccount
            postUserBean.slogan = slogan
            postUserBean.wechatAccount = wechatAccount
        }
        return postUserBean
    }


    fun getMyInfoFromNet(callback: (UserBean) -> Unit, token: String = "") {
        val service: GetMyInfoService = if (token.isEmpty()) {
            IdeaApi.getApiService(GetMyInfoService::class.java)
        } else {
            IdeaApi.getApiService(GetMyInfoService::class.java, false, OtherTokenInterceptor(token))
        }
        service.getMyInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val info = setInfo(it)
                callback(info)
                saveInDB(info)
            },{
                getMyInfoFromDB(callback)
            })

    }

    fun getMyInfoFromDB(callback: (UserBean) -> Unit) {
        MyApplication.getUserId()?.let {
            Log.d("aaa", "id $it")
            userInfoDao.getUserInfo(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    Log.d("aaa", "getMyInfoFromDB $user")
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
        private var instance: MyInfoRepository? = null

        fun getInstance(userInfoDao: UserInfoDao, loginDao: LoginDao) =
            instance ?: synchronized(this) {
                instance ?: MyInfoRepository(userInfoDao, loginDao).also { instance = it }
            }
    }
}