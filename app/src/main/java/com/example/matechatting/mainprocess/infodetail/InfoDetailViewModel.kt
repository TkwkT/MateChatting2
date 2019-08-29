package com.example.matechatting.mainprocess.infodetail

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.matechatting.bean.UserBean

class InfoDetailViewModel(private val repository: InfoDetailRepository) : ViewModel() {
    val detailName = ObservableField<String>("未填写")
    val detailMajor = ObservableField<String>("未填写")
    val detailGraduate = ObservableField<String>("未填写")
    val detailCompany = ObservableField<String>("未填写")
    val detailJob = ObservableField<String>("未填写")
    val detailDirection = ObservableField<String>("未填写")
    val detailQQ = ObservableField<String>("未填写")
    val detailWeixin = ObservableField<String>("未填写")
    val detailEmile = ObservableField<String>("未填写")
    val detailCity = ObservableField<String>("未填写")
    val detailSlogan = ObservableField<String>("未填写")
    val defaltString = "未填写"
    val defaultSlogan = "快乐生活每一天"

    fun getDetail(id: Int, callback: (UserBean) -> Unit) {
        repository.getDetail(id) {
            callback(it)
            it.apply {
                if (name.isEmpty()) {
                    detailName.set(defaltString)
                } else {
                    detailName.set(name)
                }
                if (majorName.isEmpty()) {
                    detailMajor.set(defaltString)
                } else {
                    detailMajor.set(majorName)
                }
                if (graduation.isEmpty()) {
                    detailGraduate.set(defaltString)
                } else {
                    detailGraduate.set(graduation)
                }
                if (company.isEmpty()) {
                    detailCompany.set(defaltString)
                } else {
                    detailCompany.set(company)
                }
                if (job.isEmpty()) {
                    detailJob.set(defaltString)
                } else {
                    detailJob.set(job)
                }
                if (direction.isEmpty()) {
                    detailDirection.set(defaltString)
                } else {
                    detailDirection.set(direction)
                }
                if (qqAccount.toString().isEmpty() || qqAccount == 0) {
                    detailQQ.set(defaltString)
                } else {
                    detailQQ.set(qqAccount.toString())
                }
                if (wechatAccount.isEmpty()) {
                    detailWeixin.set(defaltString)
                } else {
                    detailWeixin.set(wechatAccount)
                }
                if (email.isEmpty()) {
                    detailEmile.set(defaltString)
                } else {
                    detailEmile.set(email)
                }
                if (city.isEmpty()) {
                    detailCity.set(defaltString)
                } else {
                    detailCity.set(city)
                }
                if (slogan.isEmpty()) {
                    detailSlogan.set(defaultSlogan)
                } else {
                    detailSlogan.set(slogan)
                }
            }
        }
    }


}