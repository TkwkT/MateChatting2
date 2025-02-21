package com.example.matechatting.database

import androidx.room.*
import com.example.matechatting.bean.UserBean
import io.reactivex.Single

@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserInfo(userBean: UserBean): Single<Long>

    @Query("SELECT * FROM user_info WHERE id = :id")
    fun getUserInfo(id: Int): Single<UserBean>

    @Query("SELECT id FROM user_info WHERE state = 3 OR state = 4")
    fun getAllFriendId(): Single<List<Int>>

    @Query("UPDATE user_info SET profile_photo = :path WHERE id = :id")
    fun updateHeadImage(path: String, id: Int): Single<Int>

    @Query("UPDATE user_info SET state = :state WHERE id = :id")
    fun updateStateById(state: Int, id: Int): Single<Int>

    @Query("SELECT * FROM user_info WHERE state = :state")
    fun getUserInfoByState(state: Int): Single<List<UserBean>>

    @Query("SELECT * FROM user_info WHERE state = 3 or state = 4")
    fun getAllFriend(): Single<List<UserBean>>

    @Delete
    fun deleteUserInfo(userBean: UserBean): Single<Int>

    @Query("SELECT * FROM user_info WHERE city LIKE '%'||:key||'%' OR company LIKE '%'||:key||'%' OR email LIKE '%'||:key||'%' OR job LIKE '%'||:key||'%' OR major_name LIKE '%'||:key||'%' OR name LIKE '%'||:key||'%' OR phone_number LIKE '%'||:key||'%' OR qq_account LIKE '%'||:key||'%' OR stu_id LIKE '%'||:key||'%' OR wechat_account LIKE '%'||:key||'%' OR direction LIKE '%'||:key||'%' OR graduation LIKE '%'||:key||'%'")
    fun selectUserByKey(key: String): Single<List<UserBean>>

    @Query("UPDATE user_info SET on_line = :state WHERE id  = :id")
    fun updateOnLineState(state: Boolean, id: Int):Single<Int>

}