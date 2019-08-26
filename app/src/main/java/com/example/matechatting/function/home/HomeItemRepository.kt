package com.example.matechatting.function.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.matechatting.MyApplication
import com.example.matechatting.PAGE
import com.example.matechatting.base.BaseRepository
import com.example.matechatting.bean.HomeItemBean
import com.example.matechatting.database.AppDatabase
import com.example.matechatting.database.HomeItemDao
import com.example.matechatting.network.GetHomeItemService
import com.example.matechatting.network.IdeaApi
import com.example.matechatting.utils.ExecuteOnceObserver
import com.example.matechatting.utils.runOnNewThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.StringBuilder

class HomeItemRepository(private val homeItemDao: HomeItemDao) : BaseRepository {
    private var first = 0

    fun getHomeFromDB(callback: (List<HomeItemBean>) -> Unit) {
        homeItemDao.getHomeItemLimit(getFirst(), 20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //                first += it.size
                Log.d("aaa", "first" + first)
                callback(it)
            }, {})
    }

    private fun getFirst(): Int {
        return (++first) * 20
    }

    fun getHomeFromNet(callback: (List<HomeItemBean>) -> Unit) {
        IdeaApi.getApiService(
            GetHomeItemService::class.java,
            false
        ).getHomeItem(getPage())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("aaa","getHomeFromNet" + it.toString())
                val list = setDirection(it)
                addToDB(list)
                callback(list)
            }, {})
    }

    private fun addToDB(list: List<HomeItemBean>) {
        val homeItemDao = AppDatabase.getInstance(MyApplication.getContext()).homeItemDao()
            homeItemDao.insertHomeItems(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("aaa","保存成功 $it")
                },{})

    }

    private fun getPage(): Int {
        val start = PAGE[0]
        if (PAGE.size != 0){
            PAGE.remove(start)
        }

        return start
    }

    private fun setDirection(list: List<HomeItemBean>): List<HomeItemBean> {
        val arrayList = ArrayList<HomeItemBean>()
        for (h: HomeItemBean in list) {
            if (h != null) {
                arrayList.add(h)
            }
        }
        for (h: HomeItemBean in arrayList) {
            val ds = h.drec
            if (!ds.isNullOrEmpty()) {
                val sb = StringBuilder()
                sb.append(ds[0] + " ")
                sb.append(ds[1] + " ")
                sb.append(ds[2])
                h.direction = sb.toString()
            }
            val stringBuilder = StringBuilder()
            stringBuilder.append(h.graduationYear.toString())
            stringBuilder.append("年毕业生")
            h.graduation = stringBuilder.toString()
        }
        return arrayList
    }

    fun getHomeItemFromDBPaging(callback: (LiveData<PagedList<HomeItemBean>>) -> Unit) {
        val detailList = LivePagedListBuilder(
            homeItemDao.getHomeItem(), PagedList
                .Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(ENABLE_PLACEHOLDER).build()
        ).build()
        callback(detailList)
    }

    fun getHomeItemFromNetPaging(callback: (LiveData<PagedList<HomeItemBean>>) -> Unit) {
        val homeItemList = LivePagedListBuilder(
            PagingHomeItemDataSourceFactory(), PagedList
                .Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(ENABLE_PLACEHOLDER).build()
        ).build()
        callback(homeItemList)
    }

    companion object {
        private const val PAGE_SIZE = 15
        private const val ENABLE_PLACEHOLDER = true

        @Volatile
        private var instance: HomeItemRepository? = null

        fun getInstance(homeItemDao: HomeItemDao) =
            instance ?: synchronized(this) {
                instance
                    ?: HomeItemRepository(homeItemDao).also { instance = it }
            }
    }
}