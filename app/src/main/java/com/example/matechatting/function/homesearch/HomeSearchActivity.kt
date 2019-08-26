package com.example.matechatting.function.homesearch

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.matechatting.LOGIN_REQUEST_CODE
import com.example.matechatting.base.BaseActivity
import com.example.matechatting.base.BaseFragment
import com.example.matechatting.bean.SearchBean
import com.example.matechatting.databinding.ActivitySearchBinding
import com.example.matechatting.listener.EditTextTextChangeListener
import com.example.matechatting.utils.InjectorUtils
import com.example.matechatting.utils.NetworkState
import com.example.matechatting.utils.ToastUtil
import com.example.matechatting.utils.isNetworkConnected
import com.example.matechatting.utils.statusbar.StatusBarUtil


class HomeSearchActivity : BaseActivity<ActivitySearchBinding>() {
    private lateinit var back: FrameLayout
    private lateinit var edit: EditText
    private lateinit var clear: ImageView
    private lateinit var button: ImageView
    private lateinit var frameLayout: FrameLayout
    lateinit var viewModel: HomeSearchViewModel
    var resultArray = ArrayList<SearchBean.Payload.MyArray.Map>()
    var key = ""
    var callback: (List<SearchBean.Payload.MyArray.Map>) -> Unit = { it ->
        Log.d("aaa", it.toString())
        if (it.isEmpty()) {
            ToastUtil().setToast(this, "当前关键字无搜索结果")
        } else {
            resultArray.clear()
            resultArray.addAll(it)
            replaceFragment(ResultFragment())
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
        StatusBarUtil.setStatusBarDarkTheme(this, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setStatusBarColor(this, this.getColor(com.example.matechatting.R.color.bg_ffffff))
        }
        canSlideFinish(true)
        initView()
        initEdit()
        initClear()
        initViewModel()
        initButton()
        initBack()
        replaceFragment(HomeSearchPopularFragment())
    }

    fun getData() {
        if (isNetworkConnected(this) == NetworkState.NONE) {
            ToastUtil().setToast(this, "当前网络未连接")
        } else {
            viewModel.getResult(key, 1, 20, callback)
        }
    }

    private fun initViewModel() {
        val factory = InjectorUtils.provideHomeSearchViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(HomeSearchViewModel::class.java)
    }

    private fun initView() {
        binding.apply {
            back = searchBack
            edit = searchEditText
            clear = searchClearImage
            button = searchButton
            frameLayout = searchFrame
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(com.example.matechatting.R.id.search_frame, fragment)
        transaction.setCustomAnimations(
            com.example.matechatting.R.anim.alpha_in,
            com.example.matechatting.R.anim.alpha_out
        )
        transaction.commit()
    }

    private fun initButton() {
        button.setOnClickListener {
            doOnSearch()
        }
    }

    private fun doOnSearch() {
        val str = edit.text.toString().trim()
        if (str.isEmpty()) {
            ToastUtil().setToast(this, "关键字不能为空或空格")
        } else {
            key = str
            getData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == LOGIN_REQUEST_CODE && data != null) {
            getLoginState()
        }
    }

    private fun getLoginState() {
        val sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        BaseFragment.isLogin = sp.getBoolean("isLogin", false)
        val account = sp.getString("account", "")
        BaseFragment.account = account ?: ""
    }

    private fun initEdit() {
        edit.addTextChangedListener(
            EditTextTextChangeListener({
                if (it.isNotEmpty()) {
                    clear.visibility = View.VISIBLE
                } else {
                    clear.visibility = View.GONE
                }
            })
        )
        edit.setOnEditorActionListener { textView, id, keyEvent ->
            if (id == EditorInfo.IME_ACTION_SEARCH) {
                val imm = textView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (imm.isActive) {
                    imm.hideSoftInputFromWindow(textView.applicationWindowToken, 0)
                }
                doOnSearch()
            }
            return@setOnEditorActionListener false
        }
    }

    private fun initClear() {
        val emptyString = SpannableStringBuilder("")
        clear.setOnClickListener {
            edit.text = emptyString
        }
    }
    private fun initBack() {
        back.setOnClickListener {
            finish()
        }
    }

    override fun getLayoutId(): Int {
        return com.example.matechatting.R.layout.activity_search
    }


}
