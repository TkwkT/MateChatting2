package com.example.matechatting.function.chatting

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.RecyclerView
import com.example.matechatting.R
import com.example.matechatting.base.BaseActivity
import com.example.matechatting.databinding.ActivityChattingBinding
import com.example.matechatting.listener.EditTextTextChangeListener
import com.example.matechatting.utils.statusbar.StatusBarUtil

class ChattingActivity : BaseActivity<ActivityChattingBinding>() {
    private lateinit var back: FrameLayout
    private lateinit var title: TextView
    private lateinit var recycler: RecyclerView
    private lateinit var edit: EditText
    private lateinit var send: Button

    private var id = ""
    private var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
        StatusBarUtil.setStatusBarDarkTheme(this, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setStatusBarColor(this, this.getColor(R.color.bg_ffffff))
        }
        id = intent.getStringExtra("id") ?: throw Exception("id could not null")
        name = intent.getStringExtra("name") ?: throw Exception("name could not null")
        canSlideFinish(true)
        initBinding()
        initView()
        initEdit()
    }

    private fun initView() {
        binding.apply {
            back = chattingToolbarBack
            title = chattingToolbarTitle
            recycler = chattingRecycler
            edit = chattingMessageEdit
            send = chattingSendButton
        }
    }

    private fun initEdit() {
        edit.addTextChangedListener(
            EditTextTextChangeListener({
                if (it.isEmpty()){
                    send.isEnabled = false
                    send.background = this.getDrawable(R.drawable.shape_send_button_corner_disabled)
                }else{
                    send.isEnabled = true
                    send.background = this.getDrawable(R.drawable.shape_send_button_corner_available)
                }
            })
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_chatting
    }
}
