package com.example.matechatting.mainprocess.milelist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.matechatting.R
import com.example.matechatting.base.BaseHolder
import com.example.matechatting.bean.UserBean
import com.example.matechatting.databinding.ItemMileListFriendBinding
import com.example.matechatting.databinding.ItemMileListNewChattingBinding
import com.example.matechatting.databinding.ItemMileListNewFriendBinding

class MileListRecyclerAdapter(
    private val newFriendLayoutCallback: (Int) -> Unit,
    private val newFriendButtonCallback: (Int) -> Unit,
    private val newChattingCallback: (Int) -> Unit,
    private val friendLayoutCallback: (Int) -> Unit
) : RecyclerView.Adapter<BaseHolder>() {
    private val newFriendArray = ArrayList<UserBean>()
    private val newChattingArray = ArrayList<UserBean>()
    private val friendArray = ArrayList<UserBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        when (viewType) {
            NEW_FRIEND -> {
                Log.d("aaa", "NEW_FRIEND ")
                val binding = DataBindingUtil.inflate<ItemMileListNewFriendBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_mile_list_new_friend, parent, false
                )
                return NewFriendHolder(binding)
            }
            NEW_CHATTING -> {
                Log.d("aaa", "NEW_CHATTING ")
                val binding = DataBindingUtil.inflate<ItemMileListNewChattingBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_mile_list_new_chatting, parent, false
                )
                return NewChattingHolder(binding)
            }
            else -> {
                Log.d("aaa", "FRIEND ")
                val binding = DataBindingUtil.inflate<ItemMileListFriendBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_mile_list_friend, parent, false
                )
                return FriendHolder(binding)
            }
        }
    }

    fun freshNewFriends(list: List<UserBean>) {
        newFriendArray.clear()
        list[0].first = true
        newFriendArray.addAll(list)
    }

    fun freshNewFriend(bean: UserBean) {
        newFriendArray.add(bean)
    }

    fun freshNewChattings(list: List<UserBean>) {
        newChattingArray.clear()
        list[0].first = true
        newChattingArray.addAll(list)
    }

    fun frashNewChatting(bean: UserBean) {
        newChattingArray.add(bean)
    }

    fun frashFriends(list: List<UserBean>) {
        friendArray.clear()
        friendArray.addAll(list)
    }

    fun frashFriend(bean: UserBean) {
        friendArray.add(bean)
    }

    override fun getItemCount(): Int {
        val num = newChattingArray.size + newFriendArray.size + friendArray.size
        return num
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position >= (newFriendArray.size + newChattingArray.size) -> {
                FRIEND
            }
            position >= newFriendArray.size -> {
                NEW_CHATTING
            }
            else -> {
                NEW_FRIEND
            }
        }
    }

    private fun getNewFriendItem(position: Int): UserBean {
        return newFriendArray[position]
    }

    private fun getNewChattingItem(position: Int): UserBean {
        return newChattingArray[position - newFriendArray.size]
    }

    private fun getFriendItem(position: Int): UserBean {
        return friendArray[position - newFriendArray.size - newChattingArray.size]
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        when (holder) {
            is NewFriendHolder -> {
                val bean = getNewFriendItem(position)
                holder.bind(NewFriendSource(bean))
                holder.getLayout().setOnClickListener { newFriendLayoutCallback(bean.id) }
                holder.getButton().setOnClickListener { newFriendButtonCallback(bean.id) }
            }
            is NewChattingHolder -> {
                val bean = getNewChattingItem(position)
                holder.bind(NewChattingSource(bean))
                holder.getLayout().setOnClickListener { newChattingCallback(bean.id) }
            }
            is FriendHolder -> {
                Log.d("aaa", "FriendHolder")
                val bean = getFriendItem(position)
                holder.bind(FriendSource(bean))
                holder.getLayout().setOnClickListener { friendLayoutCallback(bean.id) }
//                holder.getButton().setOnClickListener { newFriendButtonCallback(bean.id) }
            }
        }
    }

    fun selectFirst() {

    }

    companion object {
        private const val NEW_FRIEND = 1
        private const val NEW_CHATTING = 2
        private const val FRIEND = 3
    }
}
