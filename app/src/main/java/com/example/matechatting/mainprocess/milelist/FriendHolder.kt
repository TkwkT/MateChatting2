package com.example.matechatting.mainprocess.milelist

import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.example.matechatting.R
import com.example.matechatting.base.BaseHolder
import com.example.matechatting.bean.MileItemBean
import com.example.matechatting.bean.UserBean
import com.example.matechatting.databinding.ItemMileListFriendBinding
import com.example.matechatting.utils.PinyinUtil

class FriendHolder(private val binding: ItemMileListFriendBinding) : BaseHolder(binding) {

    override fun <T> bind(t: T) {
        if (t is FriendSource) {
            val bean = t.friend
            binding.apply {
                if (bean.first) {
                    mileListTitle.visibility = View.VISIBLE
                    mileListTitle.text = bean.pinyin.substring(0, 1)
                } else {
                    mileListTitle.visibility = View.GONE
                }
                itemFriendName.text = bean.name
                itemFriendGraduate.text = bean.graduation
                itemFriendMajor.text = bean.direction
                itemFriendCompany.text = bean.company
                if (bean.onLine) {
                    mileListOnLineState.text = "在线"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        mileListOnLineState.setTextColor(context.getColor(R.color.text_green))
                    }
                } else {
                    mileListOnLineState.text = "离线"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        mileListOnLineState.setTextColor(context.getColor(R.color.text_8a8a8a))
                    }
                }
            }
        }
    }

    fun getLayout(): LinearLayout {
        return binding.itemFriendLayout
    }
}

data class FriendSource(
    val friend: UserBean
)