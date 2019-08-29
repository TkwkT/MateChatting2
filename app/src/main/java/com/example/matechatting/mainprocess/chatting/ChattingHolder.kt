package com.example.matechatting.mainprocess.chatting

import android.view.View
import com.example.matechatting.base.BaseHolder
import com.example.matechatting.base.BaseSource
import com.example.matechatting.bean.ChattingBean
import com.example.matechatting.databinding.ItemChattingBinding

class ChattingHolder(private val binding: ItemChattingBinding) : BaseHolder(binding) {
    override fun <T> bind(t: T) {
        if (t is ChattingSource) {
            val bean = t.chattingBean
            binding.chattingTimeText.text = bean.time
            if (bean.isUserSend) {
                initUser(bean)
            } else {
                initOther(bean)
            }
        }
    }

    private fun initUser(bean: ChattingBean) {
        binding.chattingOtherGroup.visibility = View.GONE
        binding.chattingUserGroup.visibility = View.VISIBLE
        binding.chattingUserMessageText.text = bean.message

    }

    private fun initOther(bean: ChattingBean) {
        binding.chattingOtherGroup.visibility = View.VISIBLE
        binding.chattingUserGroup.visibility = View.GONE
        binding.chattingOtherMessageText.text = bean.message
    }
}

data class ChattingSource(
    val chattingBean: ChattingBean
) : BaseSource()