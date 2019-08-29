package com.example.matechatting.mainprocess.myinfo

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.matechatting.BASE_URL
import com.example.matechatting.MyApplication
import com.example.matechatting.PATH
import com.example.matechatting.bean.UserBean
import com.example.matechatting.utils.NetworkState
import com.example.matechatting.utils.isNetworkConnected
import java.lang.StringBuilder

class MyInfoViewModel(private val repository: MyInfoRepository) : ViewModel() {
    val myInfoName = ObservableField<String>("未填写")
    val myInfoMajor = ObservableField<String>("未填写")
    val myInfoGraduate = ObservableField<String>("未填写")
    val myInfoCompany = ObservableField<String>("未填写")
    val myInfoJob = ObservableField<String>("未填写")
    val myInfoDirection = ObservableField<String>("未填写")
    val myInfoQQ = ObservableField<String>("未填写")
    val myInfoWeixin = ObservableField<String>("未填写")
    val myInfoEmile = ObservableField<String>("未填写")
    val myInfoCity = ObservableField<String>("未填写")
    val myInfoSlogan = ObservableField<String>("未填写")
    val myInfoHeadImage = ObservableField<String>("")
    val myInfoString = "未填写"
    val myInfoDefailtSlogan = "快乐生活每一天"

    fun getMyInfo(callback: (UserBean) -> Unit, token: String = "") {
        if (isNetworkConnected(MyApplication.getContext()) == NetworkState.NONE) {
            Log.d("aaa", "info")
            repository.getMyInfoFromDB {
                setInfoDB(it)
                callback(it)
            }
        } else {
            repository.getMyInfoFromNet({
                setInfoNet(it)
                callback(it)
            }, token)
        }
    }

    private fun setInfoDB(userBean: UserBean) {
        userBean.apply {
            Log.d("aaa", "setInfoDB $this")
            myInfoName.set(name)
            myInfoMajor.set(majorName)
            myInfoGraduate.set(graduation)
            myInfoCompany.set(company)
            myInfoJob.set(job)
            myInfoDirection.set(direction)
            myInfoQQ.set(qqAccount.toString())
            myInfoWeixin.set(wechatAccount)
            myInfoEmile.set(email)
            myInfoCity.set(city)
            myInfoSlogan.set(slogan)
            if (!headImage.isNullOrEmpty()) {
                myInfoHeadImage.set(headImage)
            }
        }
    }


    private fun setInfoNet(userBean: UserBean) {
        userBean.apply {
            myInfoName.set(name)
            myInfoMajor.set(majorName)
            myInfoGraduate.set(graduation)
            myInfoCompany.set(company)
            myInfoJob.set(job)
            myInfoDirection.set(direction)
            if (qqAccount.toString().isEmpty() || qqAccount == 0) {
                myInfoQQ.set("")
            } else {
                myInfoQQ.set(qqAccount.toString())
            }
            myInfoWeixin.set(wechatAccount)
            myInfoEmile.set(email)
            myInfoCity.set(city)
            myInfoSlogan.set(slogan)
            if (!headImage.isNullOrEmpty()) {
                val sb = StringBuilder()
                sb.append(BASE_URL)
                sb.append(PATH)
                sb.append(headImage)
                myInfoHeadImage.set(sb.toString())
                repository.saveHeadImagePath(sb.toString())
            }
        }
    }

    fun saveData(userBean: UserBean, callback: () -> Unit, token: String = "") {
        Log.d("aaa", "saveData")
        repository.saveMyInfo(userBean, callback, token)
    }
}