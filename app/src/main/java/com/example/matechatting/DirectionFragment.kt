package com.example.matechatting


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matechatting.databinding.FragmentDirectionBinding


class DirectionFragment : Fragment() {
    private lateinit var binding: FragmentDirectionBinding
    private lateinit var recycleView: RecyclerView
    private lateinit var tv_name: TextView
    private lateinit var mAdapter: DirectionAdapter
    val manager: GridLayoutManager = GridLayoutManager(this.context, 3)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_direction, container, false)
        init()
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    private fun init() {
        binding.apply {
            tv_name = tvDirectName
            recycleView = rvSmalldirect
        }
        tv_name.text = "$bundlekey:"

        /**
         * 初始化recycleView,adapter
         */
        val listValues = list[bundlekey]
        mAdapter = DirectionAdapter(listValues!!)
        recycleView.layoutManager = manager
        recycleView.adapter = mAdapter

        /**
         * 初始化mapChecked
         */
        if (!mapChecked.values.contains(true)) {
            val values = list.values
            val iterator = values.iterator()
            while (iterator.hasNext()) {
                val value = iterator.next()
                for (i in 0 until value.size)
                    mapChecked[value[i]] = false
            }
        }
    }


    companion object {
        var bundlekey = "互联网、游戏、软件"

    }
}

