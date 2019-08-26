package com.example.matechatting.function.milelist

import com.example.matechatting.base.BaseHolder
import com.example.matechatting.databinding.ItemMileListFriendBinding

class FriendHolder(binding: ItemMileListFriendBinding) :BaseHolder(binding){

    override fun <T> bind(t: T) {

    }
}
data class FriendSource(
    val friend:MileItemBean
)