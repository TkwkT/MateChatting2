package com.example.matechatting.mainprocess.milelist

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.example.matechatting.base.BaseHolder
import com.example.matechatting.bean.MileItemBean
import com.example.matechatting.bean.UserBean
import com.example.matechatting.databinding.ItemMileListNewFriendBinding

class NewFriendHolder(private val binding: ItemMileListNewFriendBinding) : BaseHolder(binding) {
    override fun <T> bind(t: T) {
        if (t is NewFriendSource) {
            val bean = t.newFriend
            binding.apply {
                if (bean.first) {
                    mileListTitle.visibility = View.VISIBLE
                    mileListTitle.text = "新好友"
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

    fun getButton(): Button {
        return binding.itemPersonChangeButton
    }
}

data class NewFriendSource(
    val newFriend: UserBean
)