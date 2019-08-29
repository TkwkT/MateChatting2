package com.example.matechatting.mainprocess.home

import androidx.recyclerview.widget.DiffUtil
import com.example.matechatting.R
import com.example.matechatting.base.BaseRecyclerAdapter
import com.example.matechatting.bean.HomeItemBean
import com.example.matechatting.databinding.ItemHomePersonBinding

class HomeItemAdapter(
    var callbackPersonButton: (Int) -> Unit,
    var callbackPersonLayout: (Int) -> Unit
) :
    BaseRecyclerAdapter<ItemHomePersonBinding, HomeItemBean, HomeItemPersonHolder, HomeItemSource>() {
    private val array = ArrayList<HomeItemBean>()

    override fun freshData(list: List<HomeItemBean>) {
        val arrayList = ArrayList<HomeItemBean>()
        arrayList.addAll(list)
        mDiffer.submitList(arrayList)

    }

    override fun onCreate(binding: ItemHomePersonBinding): HomeItemPersonHolder {
        return HomeItemPersonHolder(binding)
    }

    override fun getItem(position: Int): HomeItemSource {
        return HomeItemSource(mDiffer.currentList[position])
    }

    override fun getLayoutId(): Int {
        return R.layout.item_home_person
    }

    override fun initDiffCallback(): DiffUtil.ItemCallback<HomeItemBean> {
        return object : DiffUtil.ItemCallback<HomeItemBean>() {
            override fun areItemsTheSame(oldItem: HomeItemBean, newItem: HomeItemBean): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: HomeItemBean, newItem: HomeItemBean): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onBind(holder: HomeItemPersonHolder, position: Int) {
        getItem(position).let { bean ->
            holder.bind(bean)
            holder.getLayout().setOnClickListener { callbackPersonLayout(bean.homeItemBean.id) }
            holder.getButton().setOnClickListener { callbackPersonButton(bean.homeItemBean.id) }
        }
    }


}