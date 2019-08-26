package com.example.matechatting.utils

import android.content.Context
import com.example.matechatting.database.AppDatabase
import com.example.matechatting.function.bindphone.BindPhoneRepository
import com.example.matechatting.function.bindphone.BindPhoneViewModelFactory
import com.example.matechatting.function.changepassword.ChangePasswordByTokenRepository
import com.example.matechatting.function.changepassword.ChangePasswordByTokenViewModelFactory
import com.example.matechatting.function.cliphead.ClipRepository
import com.example.matechatting.function.cliphead.ClipViewModelFactory
import com.example.matechatting.function.direction.DirectionActivityRepository
import com.example.matechatting.function.direction.DirectionActivityViewModelFactory
import com.example.matechatting.function.direction.DirectionFragmentRepository
import com.example.matechatting.function.direction.DirectionFragmentViewModelFactory
import com.example.matechatting.function.forgetpassword.ForgetPasswordRepository
import com.example.matechatting.function.forgetpassword.ForgetPasswordViewModelFactory
import com.example.matechatting.function.forgetpassword.ResetPassRepository
import com.example.matechatting.function.forgetpassword.ResetPassViewModelFactory
import com.example.matechatting.function.home.HomeItemRepository
import com.example.matechatting.function.home.HomeItemViewModelFactory
import com.example.matechatting.function.homesearch.HomeSearchRepository
import com.example.matechatting.function.homesearch.HomeSearchViewModelFactory
import com.example.matechatting.function.infodetail.InfoDetailRepository
import com.example.matechatting.function.infodetail.InfoDetailViewModelFactory
import com.example.matechatting.function.login.LoginRepository
import com.example.matechatting.function.login.LoginViewModelFactory
import com.example.matechatting.function.mine.MineRepository
import com.example.matechatting.function.mine.MineViewModelFactory
import com.example.matechatting.function.myinfo.MyInfoRepository
import com.example.matechatting.function.myinfo.MyInfoViewModelFactory
import com.example.matechatting.otherprocess.repository.AccountRepository

object InjectorUtils {

    fun getLoginRepository(context: Context): LoginRepository {
        return LoginRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).loginDao()
        )
    }

    fun provideLoginViewModelFactory(context: Context): LoginViewModelFactory {
        val repository = getLoginRepository(context)
        return LoginViewModelFactory(repository)
    }

    fun provideBindPhoneViewModelFactory(context: Context): BindPhoneViewModelFactory {
        return BindPhoneViewModelFactory(BindPhoneRepository())
    }

    fun provideChangePasswordByTokenViewModelFactory(context: Context): ChangePasswordByTokenViewModelFactory {
        return ChangePasswordByTokenViewModelFactory(
            ChangePasswordByTokenRepository()
        )
    }

    fun provideForgetPasswordViewModelFactory(context: Context): ForgetPasswordViewModelFactory {
        return ForgetPasswordViewModelFactory(ForgetPasswordRepository())
    }

    fun provideResetPassViewModelFactory(context: Context): ResetPassViewModelFactory {
        return ResetPassViewModelFactory(ResetPassRepository())
    }

    fun provideClipViewModelFactory(context: Context): ClipViewModelFactory {
        return ClipViewModelFactory(ClipRepository())
    }

    fun getHomeItemRepository(context: Context): HomeItemRepository {
        return HomeItemRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).homeItemDao()
        )
    }

    fun provideHomeItemViewModelFactory(context: Context): HomeItemViewModelFactory {
        return HomeItemViewModelFactory(getHomeItemRepository(context))
    }

    fun getInfoDetailRepository(context: Context): InfoDetailRepository {
        return InfoDetailRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).userInfoDao()
        )
    }

    fun provideInfoDetailViewModelFactory(context: Context): InfoDetailViewModelFactory {
        return InfoDetailViewModelFactory(getInfoDetailRepository(context))
    }

    fun getMineRepository(context: Context): MineRepository {
        return MineRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).userInfoDao()
        )
    }

    fun provideMineViewModelFactory(context: Context): MineViewModelFactory {
        return MineViewModelFactory(getMineRepository(context))
    }

    fun getAccountRepository(context: Context): AccountRepository {
        return AccountRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).loginDao()
        )
    }

    fun getMyInfoRepository(context: Context): MyInfoRepository {
        return MyInfoRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).userInfoDao(),
            AppDatabase.getInstance(context.applicationContext).loginDao()
        )
    }

    fun provideMyInfoViewModelFactory(context: Context): MyInfoViewModelFactory {
        return MyInfoViewModelFactory(getMyInfoRepository(context))
    }

    fun provideHomeSearchViewModelFactory(context: Context): HomeSearchViewModelFactory {
        return HomeSearchViewModelFactory(HomeSearchRepository())
    }

    fun provideDirectionActivityViewModelFactory(context: Context): DirectionActivityViewModelFactory {
        return DirectionActivityViewModelFactory(DirectionActivityRepository())
    }

    fun provideDirectionFragmentViewModelFactory(context: Context): DirectionFragmentViewModelFactory {
        return DirectionFragmentViewModelFactory(DirectionFragmentRepository())
    }


//
//    fun getDetailRepository(context: Context): DetailRepository {
//        return DetailRepository.getInstance(
//            AppDatabase.getInstance(context.applicationContext).detailDao()
//        )
//    }
//
//    fun provideInformationDetailViewModelFactory(context: Context): InformationDetailViewModelFactory {
//        val repository = getDetailRepository(context)
//        return InformationDetailViewModelFactory(repository)
//    }
//
//    fun getCollectionDetailRepository(context: Context): CollectionDetailRepository {
//        return CollectionDetailRepository.getInstance(
//            AppDatabase.getInstance(context.applicationContext).collectionDetailDao()
//        )
//    }
//
//    fun provideCollectionDetailViewModelFactory(context: Context): CollectionDetailViewModelFactory {
//        val repository = getCollectionDetailRepository(context)
//        return CollectionDetailViewModelFactory(repository)
//    }
//
//    fun getDetailActivityRepository(context: Context): DetailActivityRepository {
//        return DetailActivityRepository.getInstance(
//            AppDatabase.getInstance(context.applicationContext).collectionDetailDao()
//        )
//    }
//
//    fun provideDetailActivityViewModelFactory(context: Context): DetailActivityViewModelFactory {
//        val repository = getDetailActivityRepository(context)
//        return DetailActivityViewModelFactory(repository)
//    }
}