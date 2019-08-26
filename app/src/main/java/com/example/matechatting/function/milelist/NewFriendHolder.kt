package com.example.matechatting.function.milelist

import com.example.matechatting.base.BaseHolder
import com.example.matechatting.databinding.ItemMileListNewFriendBinding

class NewFriendHolder(binding: ItemMileListNewFriendBinding) :BaseHolder(binding){
    override fun <T> bind(t: T) {

    }
}
data class NewFriendSource(
    val newFriend:MileItemBean
)