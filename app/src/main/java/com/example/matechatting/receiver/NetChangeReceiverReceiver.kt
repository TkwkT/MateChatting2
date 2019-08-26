package com.example.matechatting.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.matechatting.MyApplication
import com.example.matechatting.otherprocess.service.NetService
import com.example.matechatting.utils.NetworkState
import com.example.matechatting.utils.ToastUtil
import com.example.matechatting.utils.isNetworkConnected

class NetChangeReceiverReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        ToastUtil().setToast(MyApplication.getContext(),"运行")
            val tIntent = Intent(context, NetService::class.java)
            context.startService(tIntent)
    }
}
