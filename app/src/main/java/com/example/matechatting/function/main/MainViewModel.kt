package com.example.matechatting.function.main

import com.example.matechatting.PAGE
import com.example.matechatting.network.GetHomeItemPageService
import com.example.matechatting.network.IdeaApi
import com.example.matechatting.utils.runOnNewThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainViewModel {
    fun getPage() {
        IdeaApi.getApiService(GetHomeItemPageService::class.java, false).getPage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getArray(it.page)
            }, {})
    }

    private fun getArray(page: Int) {
        runOnNewThread {
            val randNum = Random(20)
            val array = IntArray(page)
            var i = 0
            while (i < page) {
                array[i] = randNum.nextInt(100) % 100
                for (j in 0 until i) {
                    if (array[j] == array[i]) {
                        i--
                        break
                    }
                }
                i++
            }
            PAGE.clear()
            for (j: Int in array) {
                PAGE.add(j)
            }
        }

    }
}