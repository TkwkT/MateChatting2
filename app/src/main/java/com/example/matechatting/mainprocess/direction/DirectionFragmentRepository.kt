package com.example.matechatting.mainprocess.direction

import android.annotation.SuppressLint
import android.util.Log
import com.example.matechatting.base.BaseRepository
import com.example.matechatting.bean.Direction
import com.example.matechatting.bean.DirectionBean
import com.example.matechatting.bean.SaveDirectionBean
import com.example.matechatting.bean.SmallDirectionBean
import com.example.matechatting.database.DirectionDao
import com.example.matechatting.database.UserInfoDao
import com.example.matechatting.mainprocess.milelist.MileListRepository
import com.example.matechatting.network.GetSmallDirectionService
import com.example.matechatting.network.IdeaApi
import com.example.matechatting.utils.runOnNewThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DirectionFragmentRepository(private val directionDao: DirectionDao) : BaseRepository {

    @SuppressLint("CheckResult")
    fun getSmallDirection(bigDirectionId: Int, callback: (SaveDirectionBean) -> Unit) {
        IdeaApi.getApiService(GetSmallDirectionService::class.java).geySmallDirection(bigDirectionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val saveDirectionBean = smallToSave(it)
                Log.d("aaa", "getSmallDirection" + saveDirectionBean.toString())
                saveBean(saveDirectionBean)
                callback(saveDirectionBean)
            }, {})
    }

    private fun saveBean(saveDirectionBean: SaveDirectionBean) {
        if (saveDirectionBean.normalDirectionList.isNullOrEmpty()) {
            for (normal: SaveDirectionBean.NormalDirection in saveDirectionBean.normalDirectionList!!) {
                if (normal.direction == null && !normal.smallDirection.isNullOrEmpty()) { //只有小方向
                    for (small: Direction in normal.smallDirection!!) {
                        small.apply {
                            val bean = DirectionBean(
                                directionName,
                                id,
                                saveDirectionBean.bigDirectionId,
                                saveDirectionBean.bigDirectionId
                            )
                            runOnNewThread {
                                directionDao.insertDirection(bean)
                            }
                            //插入数据库
                        }
                    }
                } else if (normal.direction != null && !normal.smallDirection.isNullOrEmpty()) { //中方向和小方向
                    val normalId = normal.direction!!.id
                    normal.direction?.apply {
                        //中标签无的大标签id为0
                        val bean = DirectionBean(directionName, id, saveDirectionBean.bigDirectionId)
                        runOnNewThread {
                            directionDao.insertDirection(bean)
                        }
                        //插入数据库
                    }
                    for (small: Direction in normal.smallDirection!!) {
                        small.apply {
                            val bean = DirectionBean(directionName, id, normalId, saveDirectionBean.bigDirectionId)
                            runOnNewThread {
                                directionDao.insertDirection(bean)
                            }
                            //插入数据库
                        }
                    }
                }else{

                }
            }
        }
    }

    fun selectDirection(bigId:Int,callback: (SaveDirectionBean) -> Unit){
        directionDao.selectDirectionByParent(bigId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{})
    }

    private fun smallToSave(smallDirectionBean: SmallDirectionBean): SaveDirectionBean {
        var saveDirectionBean = SaveDirectionBean()
        if (smallDirectionBean == null) {
            return saveDirectionBean
        }
        var isNull = false
        val bigDirectionId = smallDirectionBean.direction.id
        val normalArray = ArrayList<SaveDirectionBean.NormalDirection>()
        val nullNormal = Direction()
        val nullSmallArray = ArrayList<Direction>()
        for (c: SmallDirectionBean.ChildrenNormal in smallDirectionBean.children) {
            if (c.children.isNullOrEmpty()) {
                isNull = true
                nullSmallArray.add(c.direction)
            } else {
                val smallArray = ArrayList<Direction>()
                for (s: SmallDirectionBean.ChildrenNormal.Children in c.children) {
                    smallArray.add(s.direction)
                }
                val save = SaveDirectionBean.NormalDirection(c.direction, smallArray)
                normalArray.add(save)
            }
        }
        saveDirectionBean = if (isNull) {
            val save = SaveDirectionBean.NormalDirection(nullNormal, nullSmallArray)
            SaveDirectionBean(bigDirectionId, arrayListOf(save))
        } else {
            SaveDirectionBean(bigDirectionId, normalArray)
        }
        return saveDirectionBean
    }


    companion object {
        @Volatile
        private var instance: DirectionFragmentRepository? = null

        fun getInstance(directionDao: DirectionDao) =
            instance ?: synchronized(this) {
                instance ?: DirectionFragmentRepository(directionDao).also { instance = it }
            }
    }
}