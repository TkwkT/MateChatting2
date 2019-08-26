package com.example.matechatting.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.matechatting.bean.AccountBean
import com.example.matechatting.bean.HomeItemBean
import com.example.matechatting.bean.UserBean

@Database(entities = [AccountBean::class, HomeItemBean::class,UserBean::class],version = 1, exportSchema = false)
abstract class AppDatabase :RoomDatabase(){
    abstract fun loginDao(): LoginDao
    abstract fun homeItemDao(): HomeItemDao
    abstract fun userInfoDao():UserInfoDao

    companion object{
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "mate_chatting").build()
        }
    }
}