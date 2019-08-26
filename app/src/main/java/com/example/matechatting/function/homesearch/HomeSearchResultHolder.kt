package com.example.matechatting.function.homesearch

import android.widget.Button
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.matechatting.base.BaseHolder
import com.example.matechatting.bean.SearchBean
import com.example.matechatting.databinding.ItemSearchResultBinding

class HomeSearchResultHolder(private val binding: ItemSearchResultBinding) : BaseHolder(binding) {

    override fun <T> bind(t: T) {
        if (t is HomeSearchResultSource) {
            val searchBean = t.data
            binding.apply {
                itemSearchName.text = searchBean.name
                itemSearchGraduate.text = searchBean.graduation
                itemSearchMajor.text = searchBean.direction
                itemSearchCompany.text = searchBean.company
                if (!searchBean.headImage.isNullOrEmpty()) {
                    Glide.with(context).load(searchBean.headImage).into(itemSearchHead)
                }
            }
        }
    }

    fun getLayout(): LinearLayout {
        return binding.itemSearchLayout
    }

    fun getButton(): Button {
        return binding.itemSearchChangeButton
    }

}

data class HomeSearchResultSource(
    val data: SearchBean.Payload.MyArray.Map
)