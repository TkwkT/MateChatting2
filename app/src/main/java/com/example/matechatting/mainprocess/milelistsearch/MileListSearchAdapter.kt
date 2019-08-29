//package com.example.matechatting.mainprocess.milelistsearch
//
//import android.icu.lang.UCharacter.GraphemeClusterBreak.T
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.example.matechatting.R
//import com.example.matechatting.base.BaseHolder
//import com.example.matechatting.databinding.ItemMileListNewFriendBinding
//import com.example.matechatting.mainprocess.milelist.NewFriendHolder
//
//class MileListSearchAdapter : RecyclerView.Adapter<BaseHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
//        val binding = DataBindingUtil.inflate<ItemMileListNewFriendBinding>(
//            LayoutInflater.from(parent.context),
//            R.layout.item_mile_list_new_friend, parent, false
//        )
//        return NewFriendHolder(binding)
//    }
//
//    override fun getItemCount(): Int {
//    }
//
//    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
//    }
//
//}