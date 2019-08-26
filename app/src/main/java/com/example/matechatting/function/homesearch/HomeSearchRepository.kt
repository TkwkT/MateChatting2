package com.example.matechatting.function.homesearch

import android.util.Log
import com.example.matechatting.base.BaseRepository
import com.example.matechatting.bean.SearchBean
import com.example.matechatting.bean.UserBean
import com.example.matechatting.network.IdeaApi
import com.example.matechatting.network.SearchService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.StringBuilder

class HomeSearchRepository : BaseRepository {

    fun getResult(key: String, page: Int, size: Int = 20, callback: (List<SearchBean.Payload.MyArray.Map>) -> Unit) {
        IdeaApi.getApiService(SearchService::class.java).getResult(key, page, size)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.success){
                    callback(setInfo(it.payload))
                }else{
                    callback(ArrayList())
                }

            }, {})
    }

    private fun setInfo(resultBean: SearchBean.Payload): ArrayList<SearchBean.Payload.MyArray.Map> {
        val array = ArrayList<SearchBean.Payload.MyArray.Map>()
        if (resultBean.empty) {
            return array
        }
        val dataList = resultBean.myArrayList
        for (b: SearchBean.Payload.MyArray in dataList) {
            if (!b.empty) {
                array.add(b.map)
            }
        }
        for (a: SearchBean.Payload.MyArray.Map in array) {
            a.apply {
                if (!directions.empty) {
                    val sb = StringBuilder()
                    sb.append(directions.myArrayList[0] + " ")
                    sb.append(directions.myArrayList[1] + " ")
                    sb.append(directions.myArrayList[2])
                    direction = sb.toString()
                }
                val sb = StringBuilder()
                sb.append(graduationYear)
                sb.append("级毕业")
                graduation = sb.toString()
            }
        }
        return array
    }
}