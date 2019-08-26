package com.example.matechatting.function.infodetail

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import com.example.matechatting.R
import com.example.matechatting.base.BaseActivity
import com.example.matechatting.databinding.ActivityInfoDetailBinding
import com.example.matechatting.utils.InjectorUtils
import com.example.matechatting.utils.statusbar.StatusBarUtil


class InfoDetailActivity : BaseActivity<ActivityInfoDetailBinding>() {
    private lateinit var back: FrameLayout
    private lateinit var viewModel: InfoDetailViewModel
    private lateinit var changeButton: Button
    private lateinit var chattingButton:Button

    private var id = 0
    private var isChatting = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = intent.getIntExtra("id",0)
        isChatting = intent.getBooleanExtra("is_chatting",false)
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
        StatusBarUtil.setStatusBarDarkTheme(this, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setStatusBarColor(this, this.getColor(R.color.bg_ffffff))
        }
        canSlideFinish(true)
        initBinding()
        initView()
        initBack()
    }

    private fun initView() {
        val factory = InjectorUtils.provideInfoDetailViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(InfoDetailViewModel::class.java)
        back = binding.infoDetailBack
        viewModel.getDetail(id)
        binding.viewmodel = viewModel
    }

    private fun initBack(){
        back.setOnClickListener {
            finish()
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_info_detail
    }

}
