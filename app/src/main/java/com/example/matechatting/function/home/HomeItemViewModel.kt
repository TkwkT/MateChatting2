package com.example.matechatting.function.home

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.matechatting.bean.HomeItemBean
import com.example.matechatting.utils.NetworkState
import com.example.matechatting.utils.isNetworkConnected

class HomeItemViewModel(private val repository: HomeItemRepository) : ViewModel() {
    val dataList = MutableLiveData<List<HomeItemBean>>()
    private val arrayList = ArrayList<HomeItemBean>()
    private var isLoading = false

    fun getDataPaging(context: Context, callback: (LiveData<PagedList<HomeItemBean>>) -> Unit) {
        if (isNetworkConnected(context) == NetworkState.NONE) {
            repository.getHomeItemFromDBPaging(callback)
        } else {
            repository.getHomeItemFromNetPaging(callback)
        }
    }

    fun getDataNormal(context: Context) {
        if (isNetworkConnected(context) == NetworkState.NONE) {
            repository.getHomeFromDB {
                arrayList.addAll(it)
                dataList.value = arrayList
            }
        } else {
            repository.getHomeFromNet {
                arrayList.addAll(it)
                dataList.value = arrayList
            }
        }
    }
}