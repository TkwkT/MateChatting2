package com.example.matechatting.function.home


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.matechatting.R
import com.example.matechatting.function.infodetail.InfoDetailActivity
import com.example.matechatting.function.login.LoginActivity
import com.example.matechatting.LOGIN_REQUEST_CODE
import com.example.matechatting.PAGE
import com.example.matechatting.databinding.FragmentHomeBinding
import com.example.matechatting.base.BaseFragment
import com.example.matechatting.bean.HomeItemBean
import com.example.matechatting.function.homesearch.HomeSearchActivity
import com.example.matechatting.listener.RecyclerScrollListener
import com.example.matechatting.utils.InjectorUtils

/**
 * 交换名片按钮点击事件未完成
 * @link [initLogin]
 */
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeButton: Button
    private lateinit var viewModel: HomeItemViewModel
    private lateinit var adapter: HomeItemAdapter
    private lateinit var callbackPersonButton: () -> Unit
    private lateinit var callbackPersonLayout: (Int) -> Unit
    private lateinit var scrollListener: RecyclerScrollListener


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        init()
        initRecyclerView()
        setCallbackToAdapter()
        return binding.root
    }

    override fun initView() {
        val factory = InjectorUtils.provideHomeItemViewModelFactory(requireContext())
        viewModel = ViewModelProviders.of(this, factory).get(HomeItemViewModel::class.java)
        viewModel.getDataNormal(requireContext())
        recyclerView = binding.homeRecyclerView
        homeButton = binding.homeButtonSearch
        initSearchButton()
    }

    private fun initScrollListener(layoutManager: LinearLayoutManager) {
        scrollListener = object : RecyclerScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return PAGE.isEmpty()
            }

            override fun loadMoreItems() {
                Log.d("aaa","loadMoreItems 调用")
                viewModel.getDataNormal(requireContext())
                viewModel.dataList.observe(this@HomeFragment, Observer { list ->
                    if (list != null) {
                        adapter.freshData(list)
                    }
                })
            }
        }
    }

    private fun initSearchButton() {
        homeButton.setOnClickListener {
            val intent = Intent(requireActivity(), HomeSearchActivity::class.java)
            transferActivity(intent, 0x999)
        }
    }

    private fun initRecyclerView() {
        adapter = HomeItemAdapter(callbackPersonButton, callbackPersonLayout)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.isNestedScrollingEnabled = false
        initScrollListener(layoutManager)
        recyclerView.addOnScrollListener(scrollListener)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        viewModel.dataList.observe(this, Observer { list ->
            if (list != null) {
                Log.d("aaa", "initAdapter$list")
                adapter.freshData(list)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        init()
        setCallbackToAdapter()
    }

    private fun transferActivity(intent: Intent, requestCode: Int) {
        requireActivity().startActivityForResult(intent, requestCode)
    }

    private fun setCallbackToAdapter() {
        adapter.callbackPersonButton = callbackPersonButton
        adapter.callbackPersonLayout = callbackPersonLayout
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

}
