package com.example.matechatting.function.direction

import android.annotation.SuppressLint
import android.util.Log
import com.example.matechatting.base.BaseRepository
import com.example.matechatting.bean.Direction
import com.example.matechatting.bean.SaveDirectionBean
import com.example.matechatting.bean.SmallDirectionBean
import com.example.matechatting.network.GetSmallDirectionService
import com.example.matechatting.network.IdeaApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DirectionFragmentRepository : BaseRepository {

    @SuppressLint("CheckResult")
    fun getSmallDirection(bigDirectionId: Int, callback: (SaveDirectionBean) -> Unit) {
        IdeaApi.getApiService(GetSmallDirectionService::class.java).geySmallDirection(bigDirectionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val saveDirectionBean = smallToSave(it)
                Log.d("aaa", "getSmallDirection" + saveDirectionBean.toString())
                callback(saveDirectionBean)
            }, {})
    }

    private fun smallToSave(smallDirectionBean: SmallDirectionBean): SaveDirectionBean {
        var saveDirectionBean = SaveDirectionBean()
        if (smallDirectionBean == null) {
            return saveDirectionBean
        }
        var isNull = false
        val bigDirectionId = smallDirectionBean.direction.id
        val normalArray = ArrayList<SaveDirectionBean.NormalDirection>()
        val nullNormal = Direction()
        val nullSmallArray = ArrayList<Direction>()
        for (c: SmallDirectionBean.ChildrenNormal in smallDirectionBean.children) {
            if (c.children.isNullOrEmpty()) {
                isNull = true
                nullSmallArray.add(c.direction)
            } else {
                val smallArray = ArrayList<Direction>()
                for (s: SmallDirectionBean.ChildrenNormal.Children in c.children) {
                    smallArray.add(s.direction)
                }
                val save = SaveDirectionBean.NormalDirection(c.direction, smallArray)
                normalArray.add(save)
            }
        }
        saveDirectionBean = if (isNull) {
            val save = SaveDirectionBean.NormalDirection(nullNormal, nullSmallArray)
            SaveDirectionBean(bigDirectionId, arrayListOf(save))
        } else {
            SaveDirectionBean(bigDirectionId, normalArray)
        }
        return saveDirectionBean
    }

}