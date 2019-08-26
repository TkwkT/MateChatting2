package com.example.matechatting.function.milelist


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.matechatting.LOGIN_REQUEST_CODE
import com.example.matechatting.function.main.MainActivity
import com.example.matechatting.function.main.MyOnTouchListener
import com.example.matechatting.R
import com.example.matechatting.databinding.FragmentMileListBinding
import com.example.matechatting.base.BaseFragment
import com.example.matechatting.function.login.LoginActivity
import com.example.matechatting.myview.SideView
import com.example.matechatting.utils.statusbar.StatusBarUtil

class MileListFragment : BaseFragment() {
    private lateinit var binding: FragmentMileListBinding
    private lateinit var toolbar: Toolbar
    private lateinit var sideView: SideView
    private lateinit var unLoginLayout: LinearLayout
    private lateinit var loginLayout: LinearLayout
    private lateinit var loginButton: Button
    private lateinit var recycler: RecyclerView

    private var changing = false
    private val myOnTouchListener = object : MyOnTouchListener {
        override fun onTouch(isScroll: Boolean) {
            val isLogin = isLogin
            if (isLogin) {
                if (isScroll && !changing) {
                    changing = true
                    sideView.visibility = View.GONE
                } else if (!isScroll) {
                    changing = false
                    sideView.visibility = View.VISIBLE
                    sideView.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.side_view_show)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mile_list, container, false)
        StatusBarUtil.setStatusBarDarkTheme(requireActivity(), true)
        init()
        return binding.root
    }

    override fun initView() {
        toolbar = binding.mileListToolbar
        sideView = binding.mileListSideview
        unLoginLayout = binding.mileListUnloginLayout
        loginLayout = binding.mileListLoginLayout
        loginButton = binding.mileListLoginButton
        recycler = binding.mileListRecycler
        (requireActivity() as? MainActivity)?.registerMyOnTouchListener(myOnTouchListener)
        initSideView()
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    /**
     * 登陆后获取数据
     * 未实现
     */
    override fun initLogin() {
        unLoginLayout.visibility = View.GONE
        loginLayout.visibility = View.VISIBLE

    }

    override fun initNotLogin() {
        loginLayout.visibility = View.GONE
        unLoginLayout.visibility = View.VISIBLE
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        loginButton.setOnClickListener {
            transferActivity(intent, LOGIN_REQUEST_CODE)
        }
    }

    private fun transferActivity(intent: Intent, requestCode: Int) {
        requireActivity().startActivityForResult(intent, requestCode)
    }


    private fun initSideView() {
        sideView.setOnTouchingLetterChangedListener(object : SideView.Companion.OnTouchingLetterChangedListener {
            override fun onTouchingLetterChanged(str: String) {
                Log.d("aaa", str)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as? MainActivity)?.unregisterMyOnTouchListener(myOnTouchListener)
    }

}

