package com.example.matechatting.function.homesearch


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.matechatting.LOGIN_REQUEST_CODE

import com.example.matechatting.R
import com.example.matechatting.base.BaseFragment
import com.example.matechatting.databinding.FragmentResultBinding
import com.example.matechatting.function.infodetail.InfoDetailActivity
import com.example.matechatting.function.login.LoginActivity

class ResultFragment : BaseFragment() {
    private lateinit var binding: FragmentResultBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HomeSearchResultAdapter
    private lateinit var callbackPersonButton: () -> Unit
    private lateinit var callbackPersonLayout: (Int) -> Unit
    private lateinit var callbackLoadMore: (Int) -> Unit
    private var page = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)
        init()
        initRecycler()
        return binding.root
    }

    override fun initView() {
        callbackLoadMore = {
            loadMore()
        }
        recyclerView = binding.searchResultRecycler
    }

    private fun initRecycler(){
        adapter = HomeSearchResultAdapter(callbackPersonButton, callbackPersonLayout, { loadMore() })
        val array = (requireActivity() as HomeSearchActivity).resultArray
        if (array.size < 20) {
            adapter.needGone = true
        }
        adapter.frashData(array)
        Log.d("aaa", "resultArray.observe + $array")

        recyclerView.adapter = adapter
    }

    private fun loadMore() {
        val activity = requireActivity() as HomeSearchActivity
        activity.viewModel.getResult(activity.key, getPage()) {
            if (it.size < 20) {
                adapter.needGone = true
            }
            adapter.frashData(it)

        }
    }

    private fun getPage(): Int {
        return ++page
    }

    override fun initLogin() {
        callbackPersonButton = {}
        callbackPersonLayout = {
            val intent = Intent(requireActivity(), InfoDetailActivity::class.java)
            intent.putExtra("id", it)
            requireActivity().startActivityForResult(intent, 0x999)
        }
    }

    override fun initNotLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        callbackPersonButton = {
            transferActivity(intent, LOGIN_REQUEST_CODE)
        }
        callbackPersonLayout = {
            transferActivity(intent, LOGIN_REQUEST_CODE)
        }
    }

    private fun transferActivity(intent: Intent, requestCode: Int) {
        requireActivity().startActivityForResult(intent, requestCode)
    }


}
