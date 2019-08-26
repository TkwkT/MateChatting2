package com.example.matechatting.function.milelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.matechatting.R
import com.example.matechatting.base.BaseHolder
import com.example.matechatting.databinding.ItemMileListFriendBinding
import com.example.matechatting.databinding.ItemMileListNewChattingBinding
import com.example.matechatting.databinding.ItemMileListNewFriendBinding

class MileListRecyclerAdapter : RecyclerView.Adapter<BaseHolder>() {
    private val newFriendArray = ArrayList<MileItemBean>()
    private val newChattingArray = ArrayList<MileItemBean>()
    private val friendArray = ArrayList<MileItemBean>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        when (viewType) {
            NEW_FRIEND -> {
                val binding = DataBindingUtil.inflate<ItemMileListNewFriendBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_mile_list_new_friend, parent, false
                )
                return NewFriendHolder(binding)
            }
            NEW_CHATTING -> {
                val binding = DataBindingUtil.inflate<ItemMileListNewChattingBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_mile_list_new_chatting, parent, false
                )
                return NewChattingHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemMileListFriendBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_mile_list_friend, parent, false
                )
                return FriendHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return newChattingArray.size + newFriendArray.size + friendArray.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position > 0 && position <= newFriendArray.size) {
            NEW_FRIEND
        } else if (position > newFriendArray.size && position <= newFriendArray.size + newChattingArray.size) {
            NEW_CHATTING
        } else {
            FRIEND
        }
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        when (holder) {
            is NewFriendHolder -> {
            }
            is NewChattingHolder -> {
            }
            else -> {
            }
        }
    }

    companion object {
        private const val NEW_FRIEND = 1
        private const val NEW_CHATTING = 2
        private const val FRIEND = 3
    }
}
