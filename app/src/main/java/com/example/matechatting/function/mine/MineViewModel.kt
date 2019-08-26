package com.example.matechatting.function.mine

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

class MineViewModel(private val repository: MineRepository) : ViewModel() {
    val mineName = ObservableField<String>("未登录")
    val mineSlogan = ObservableField<String>("快乐生活每一天")
    val mineHeadImage = ObservableField<String>("")
    val defaultSlogan = "快乐生活每一天"
    val defaultName = "未登录"

    fun getMine() {
        if (isNetworkConnected(MyApplication.getContext()) == NetworkState.NONE) {
            repository.getMineFromDB {
                setInfoDB(it)
            }
        } else {
            repository.getMineFromNet {
                setInfoNet(it)
            }
        }
        repository.getMine {

            Log.d("aaa", it.toString())
        }
    }

    private fun setInfoDB(userBean: UserBean) {
        userBean.apply {
            if (name.isNullOrEmpty()) {
                mineName.set(defaultName)
            }
            mineName.set(name)
            mineSlogan.set(slogan)
            if (!headImage.isNullOrEmpty()) {
                mineHeadImage.set(headImage)
            }
        }
    }

    private fun setInfoNet(userBean: UserBean){
        userBean.apply {
            if (name.isNullOrEmpty()) {
                mineName.set(defaultName)
            }
            mineName.set(name)
            mineSlogan.set(slogan)
            if (!headImage.isNullOrEmpty()) {
                val sb = StringBuilder()
                sb.append(BASE_URL)
                sb.append(PATH)
                sb.append(headImage)
                mineHeadImage.set(sb.toString())
                repository.saveHeadImagePath(sb.toString())
            }
        }
    }


}