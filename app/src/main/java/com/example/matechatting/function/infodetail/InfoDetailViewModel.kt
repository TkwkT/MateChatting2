package com.example.matechatting.function.infodetail

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

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

    fun getDetail(id: Int) {
        repository.getDetail(id) {
            it.apply {
                detailName.set(name)
                detailMajor.set(majorName)
                detailGraduate.set(graduation)
                detailCompany.set(company)
                detailJob.set(job)
                detailDirection.set(direction)
                detailQQ.set(qqAccount.toString())
                detailWeixin.set(wechatAccount)
                detailEmile.set(email)
                detailCity.set(city)
                detailSlogan.set(slogan)
            }
        }
    }


}