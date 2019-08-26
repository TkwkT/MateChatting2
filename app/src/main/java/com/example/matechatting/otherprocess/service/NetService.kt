package com.example.matechatting.otherprocess.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.matechatting.utils.InjectorUtils

class NetService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("aaa","service create")

//        InjectorUtils.getAccountRepository(this).getToken {
//            if (it.isNotEmpty()){
////                Client.initChannel(it)
//                NettyClient("server.natappfree.cc",41999).start(it)
//            }
//        }
    }

    override fun onBind(intent: Intent): IBinder {
        return NetBinder()
    }
}
