package com.example.matechatting.mainprocess.milelist

import android.view.View
import android.widget.LinearLayout
import com.example.matechatting.base.BaseHolder
import com.example.matechatting.bean.UserBean
import com.example.matechatting.databinding.ItemMileListNewChattingBinding

class NewChattingHolder(private val binding: ItemMileListNewChattingBinding) : BaseHolder(binding) {
    override fun <T> bind(t: T) {
        if (t is NewChattingSource) {
            val bean = t.newChatting
            binding.apply {
                if (bean.first) {
                    mileListTitle.visibility = View.VISIBLE
                    mileListTitle.text = "新会话"
                }else{
                    mileListTitle.visibility = View.GONE
                }
                itemFriendName.text = bean.name
                itemFriendGraduate.text = bean.graduation
                itemFriendMajor.text = bean.direction
                itemFriendCompany.text = bean.company
            }
        }
    }

    fun getLayout(): LinearLayout {
        return binding.itemFriendLayout
    }
}

data class NewChattingSource(
    val newChatting: UserBean
)