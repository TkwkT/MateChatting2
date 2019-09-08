package com.example.matechatting.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerScrollListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

//    override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
//        if (v?.getChildAt(v.childCount - 1) != null) {
//            if ((scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) &&
//                scrollY > oldScrollY
//            ) {
//                val visibleItemCount = layoutManager.childCount;
//                val totalItemCount = layoutManager.itemCount;
//                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
//                if (!isLastPage()) {
//                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount
//                        && pastVisibleItems >= 0
//                        && totalItemCount >= PAGE_SIZE
//                    ) {
//                        loadMoreItems()
//                    }
//                }
//            }
//        }
//    }

//    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//        super.onScrollStateChanged(recyclerView, newState)
//        if (newState==RecyclerView.SCROLL_STATE_IDLE){
//            loadMoreItems()
//        }
//
//
//    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val mLastChildPosition = layoutManager.findLastVisibleItemPosition()//当前页面最后一个可见的item的位置
        val itemTotalCount = layoutManager.itemCount//获取总的item的数量
        if (!isLastPage() && mLastChildPosition == itemTotalCount - 1) {//当前页面的最后一个item是item全部的最后一个并且当前页面的最后一个item的底部是recycleView的底部的时候，获取新数据
            loadMoreItems()
        }


//        val mLastChildPosition = layoutManager.findLastVisibleItemPosition()//当前页面最后一个可见的item的位置
//        val itemTotalCount = layoutManager.itemCount//获取总的item的数量
//        if (!isLastPage() && mLastChildPosition == itemTotalCount - 1) {//当前页面的最后一个item是item全部的最后一个并且当前页面的最后一个item的底部是recycleView的底部的时候，获取新数据
//            loadMoreItems()
//        }
    }

    abstract fun isLastPage(): Boolean

    abstract fun loadMoreItems()

    companion object {
        private const val PAGE_SIZE = 20
    }
}