package com.example.matechatting.mainprocess.milelistsearch

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.matechatting.R
import com.example.matechatting.base.BaseActivity
import com.example.matechatting.databinding.ActivityMileListSearchBinding
import com.example.matechatting.listener.EditTextTextChangeListener
import com.example.matechatting.utils.ToastUtilWarning
import com.example.matechatting.utils.statusbar.StatusBarUtil

class MileListSearchActivity : BaseActivity<ActivityMileListSearchBinding>() {
    private lateinit var back: FrameLayout
    private lateinit var edit: EditText
    private lateinit var clear: ImageView
    private lateinit var button: ImageView
    private lateinit var recycler: RecyclerView
    private lateinit var viewModel: MileListSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
        StatusBarUtil.setStatusBarDarkTheme(this, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setStatusBarColor(this, this.getColor(R.color.bg_ffffff))
        }
        canSlideFinish(true)
        initView()
        initClear()
        initEdit()
        initButton()
        initBack()
    }

    private fun initView() {
        binding.apply {
            back = mileListSearchBack
            edit = mileListSearchEditText
            clear = mileListSearchClearImage
            button = mileListSearchButton
            recycler = mileListSearchRecycler
        }
    }

    private fun initRecycler(){

    }

    private fun initClear() {
        val emptyString = SpannableStringBuilder("")
        clear.setOnClickListener {
            edit.text = emptyString
        }
    }

    private fun initButton() {
        button.setOnClickListener {
            doOnSearch()
        }
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

    private fun doOnSearch() {
        val str = edit.text.toString().trim()
        if (str.isEmpty()) {
            ToastUtilWarning().setToast(this, "关键字不能为空或空格")
        } else {
            viewModel.search(str)
        }
    }

    private fun initBack() {
        back.setOnClickListener {
            finish()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_mile_list_search
    }
}
