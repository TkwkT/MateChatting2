package com.example.matechatting.function.direction

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseIntArray
import android.widget.FrameLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.matechatting.R
import com.example.matechatting.base.BaseActivity
import com.example.matechatting.bean.BigDirectionBean
import com.example.matechatting.databinding.ActivityDirectionNewBinding
import com.example.matechatting.function.cliphead.ClipViewModel
import com.example.matechatting.utils.InjectorUtils
import com.example.matechatting.utils.statusbar.StatusBarUtil

class DirectionNewActivity : BaseActivity<ActivityDirectionNewBinding>() {
    private lateinit var back: FrameLayout
    private lateinit var recycler: RecyclerView
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: BigDirectionAdapter
    private lateinit var fragmentList: ArrayList<Fragment>
    private lateinit var viewModel: DirectionActivityViewModel
    private lateinit var recyclerCallback: (Int) -> Unit
    private lateinit var toolbarTitle: TextView
    private lateinit var bigDirectionArray: List<BigDirectionBean>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
        StatusBarUtil.setStatusBarDarkTheme(this, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setStatusBarColor(this, this.getColor(R.color.bg_ffffff))
        }
        canSlideFinish(false)
        initView()
        initCallback()
        initRecycler()
        initData()

        initBack()

    }

    private fun initView() {
        binding.apply {
            back = directionToolbarBack
            recycler = directionRecycler
            viewPager = directionViewpager
            toolbarTitle = directionToolbarTitle
        }
        val factory = InjectorUtils.provideDirectionActivityViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(DirectionActivityViewModel::class.java)
    }

    private fun initRecycler() {
        adapter = BigDirectionAdapter(recyclerCallback)
        recycler.adapter = adapter
        clickCallback = {
            Log.d("aaa","clickCallback 调用")
            adapter.setIsSelect()
        }
    }

    private fun initData() {
        viewModel.getBigDirection {
            if (!it.isNullOrEmpty()) {
                bigDirectionArray = it
                toolbarTitle.text = it[0].directionName
                initViewPager(it)
                adapter.freshData(it)
            }
        }
    }

    private fun initViewPager(list: List<BigDirectionBean>) {
        fragmentList = ArrayList()
        for (big: BigDirectionBean in list) {
            fragmentList.add(DirectionNewFragment.newInstance(big.id))
        }
        viewPager.adapter = PagerAdapter(fragmentList, supportFragmentManager)
        viewPager.currentItem = 0
        setViewPagerListener()
    }

    private fun initCallback() {
        recyclerCallback = {
            setCurrentPosition(it)
            toolbarTitle.text = bigDirectionArray[it].directionName
        }

    }

    private fun setCurrentPosition(position: Int) {
        viewPager.currentItem = position
    }

    private fun setViewPagerListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                adapter.setCurrentPosition(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        })
    }

    private fun initBack() {
        back.setOnClickListener {
            finish()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_direction_new
    }

    override fun onDestroy() {
        super.onDestroy()
        saveMap.clear()
    }

    companion object {
        val saveMap = SparseIntArray()
        var clickCallback: () -> Unit = {}
    }
}
