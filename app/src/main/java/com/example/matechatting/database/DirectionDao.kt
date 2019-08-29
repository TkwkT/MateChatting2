package com.example.matechatting.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.matechatting.bean.DirectionBean
import io.reactivex.Single

@Dao
interface DirectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDirection(directionBean: DirectionBean)

    @Query("UPDATE direction SET is_select = :isSelect WHERE id = :id")
    fun updateState(isSelect: Boolean, id: Int)

    @Query("SELECT * FROM direction WHERE parent_id = :parentId")
    fun selectDirectionByParent(parentId: Int): Single<List<DirectionBean>>
}