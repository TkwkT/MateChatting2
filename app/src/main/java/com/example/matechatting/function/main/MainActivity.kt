package com.example.matechatting.function.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.matechatting.*
import com.example.matechatting.base.BaseActivity
import com.example.matechatting.base.BaseFragment
import com.example.matechatting.databinding.ActivityMainBinding
import com.example.matechatting.function.home.HomeFragment
import com.example.matechatting.function.milelist.MileListFragment
import com.example.matechatting.function.mine.MineFragment
import com.example.matechatting.utils.statusbar.StatusBarUtil
import com.google.android.material.tabs.TabLayout


class MainActivity : BaseActivity<ActivityMainBinding>(), MainConstValue {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private val fragmentList = ArrayList<Fragment>()
    private val onTouchListeners = ArrayList<MyOnTouchListener>()

    override fun onCreate(savedInstanceState: Bundle?) {
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
        StatusBarUtil.setTranslucentStatus(this)
        super.onCreate(savedInstanceState)
        initBinding()
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            StatusBarUtil.setStatusBarColor(this, 0x55000000)
        }
        getLoginState()
        init()
        initViewPager()
        initTabLayout()
        listenNetwork()
    }

    override fun doOnNetworkAvailable() {
        super.doOnNetworkAvailable()
//        val tIntent = Intent(this, NetService::class.java)
//        this.startService(tIntent)
//        InjectorUtils.getAccountRepository(this).getToken {
//            if (it.isNotEmpty()){
//                runOnNewThread {
//                    NettyClient("server.natappfree.cc",33565).start(it)
//                }
//            }
//        }

        Log.d("aaa","doOnNetworkAvailable")
        for (i: Int in 0 until 20) {
            PAGE.add(i)
        }
        MainViewModel().getPage()
    }

    private fun init() {
        canSlideFinish(false)
        viewPager = binding.mainViewpager
        tabLayout = binding.mainTbLayout
    }

    private fun initViewPager() {
        fragmentList.add(HomeFragment())
        fragmentList.add(MileListFragment())
        fragmentList.add(MineFragment())
        viewPager.adapter = PagerAdapter(fragmentList, supportFragmentManager)
        viewPager.currentItem = 0
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                tabLayout.getTabAt(position)?.select()
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == 1) {
                    if (positionOffset != 0f) {
                        for (l: MyOnTouchListener in onTouchListeners) {
                            l.onTouch(true)
                        }
                    } else {
                        for (l: MyOnTouchListener in onTouchListeners) {
                            l.onTouch(false)
                        }
                    }
                }
            }
        })
    }

    private fun initTabLayout() {
        for (i: Int in 0 until tabText.size) {
            if (i == 0) {
                tabLayout.addTab(tabLayout.newTab().setText(tabText[0]).setIcon(tabSelectedDrawableIdList[0]))
            } else {
                tabLayout.addTab(tabLayout.newTab().setText(tabText[i]).setIcon(tabUnselectedDrawableList[i]))
            }
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.apply {
                    setIcon(tabUnselectedDrawableList[position])
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.apply {
                    setIcon(tabSelectedDrawableIdList[position])
                    viewPager.currentItem = position
                }
            }
        })
    }

    private fun getLoginState() {
        Log.d("aaa","getLoginState")
        val sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        BaseFragment.isLogin = sp.getBoolean("isLogin", false)
        val account = sp.getString("account", "")
        BaseFragment.account = account ?: ""
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == LOGIN_REQUEST_CODE && data != null) {
            getLoginState()
        } else if (resultCode == Activity.RESULT_OK && requestCode == ALBUM_REQUEST_CODE && data != null) {
            fragmentList[2].onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    fun registerMyOnTouchListener(myOnTouchListener: MyOnTouchListener) {
        onTouchListeners.add(myOnTouchListener)
    }

    fun unregisterMyOnTouchListener(myOnTouchListener: MyOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener)
    }

}

interface MyOnTouchListener {
    fun onTouch(isScroll: Boolean)
}

