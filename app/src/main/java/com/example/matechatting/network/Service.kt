package com.example.matechatting.network

import com.example.matechatting.bean.*
import com.example.matechatting.bean.HomeItemBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*


interface LoginService {
    /**
     * 登录验证接口
     * @link [stuId]: 账号
     * @link [password]: 密码
     */
    @FormUrlEncoded
    @POST("user/log_in")
    fun getLoginAndGetToken(@Field("stu_id") stuId: String, @Field("password") password: String): Observable<SPLoginBean>
}

interface SendMessageService {
    /**
     * 请求发送短信接口
     */
    @FormUrlEncoded
    @POST("/user/send_bind_code")
    fun getCheckCode(@Field("phone_number") phoneNum: Long): Observable<Boolean>
}

interface CheckCodeService {
    /**
     * 检查验证码是否输入正确接口
     */
    @FormUrlEncoded
    @POST("/user/bind_phone")
    fun checkCode(@Field("phone_number") phoneNum: Long, @Field("code") code: String): Observable<SBean>
}

interface ChangePasswordByTokenService {
    /**
     * 根据token修改密码接口
     */
    @FormUrlEncoded
    @POST("/user/token_change_password")
    fun changePassword(@Field("password") newPass: String, @Field("old_password") oldPass: String): Observable<SBean>
}

interface SendResetMessageService {
    /**
     * 在忘记密码页面请求短信验证码接口
     * 返回结果中包含用户账号
     */
    @FormUrlEncoded
    @POST("/user/get_verification_code")
    fun getResetToken(@Field("phone_number") phoneNum: Long): Observable<SPBean>
}

interface CheckResetCodeService {
    /**
     * 在忘记密码页面请求短信验证码接口
     * 返回结果为下一个接口的token
     */
    @FormUrlEncoded
    @POST("/user/get_change_token")
    fun checkCode(@Field("phone_number") phoneNum: Long, @Field("code") code: String): Observable<SPBean>
}

interface ChangeResetPassService {
    /**
     * 根据手机号改密码
     */
    @FormUrlEncoded
    @POST("/user/change_by_code")
    fun changePassword(@Field("pass") newPath: String): Observable<SBean>
}

interface PostImageService {
    /**
     * 上传头像接口
     */
    @Multipart
    @POST("/user/upload_profile_photo")
    fun postImage(@Part file: MultipartBody.Part): Observable<SBean>
}

interface GetHomeItemService {
    /**
     * 获取首页用户列表
     */
    @FormUrlEncoded
    @POST("/get_users")
    fun getHomeItem(@Field("start") start: Int): Observable<List<HomeItemBean>>
}

interface GetHomeItemPageService {
    /**
     * 获取服务器首页总页数
     */
    @POST("/get_page")
    fun getPage(): Observable<IBean>
}

interface GetUserByIdService {
    @FormUrlEncoded
    @POST("/user/get_user_by_id")
    fun getUser(@Field("id") id: Int): Observable<UserBean>
}

interface GetMineService {
    @POST("/user/get_user_by_token")
    fun getMine(): Observable<UserBean>
}

interface GetMyInfoService {
    @POST("/user/get_user_by_token")
    fun getMyInfo(): Observable<UserBean>
}

interface SearchService {
    @FormUrlEncoded
    @POST("/get_user_by_key")
    fun getResult(@Field("key") key: String, @Field("page") page: Int, @Field("size") size: Int = 20): Observable<SearchBean>
}

interface UpdateUserInfoService {
    @POST("/user/userUpdate")
    fun update(@Body() userBean: PostUserBean): Observable<SBean>
}

interface GetBigDirectionService {
    @POST("/get_large_directions")
    fun getBigDirection(): Observable<List<BigDirectionBean>>
}

interface GetSmallDirectionService {
    @FormUrlEncoded
    @POST("/get_small_directions")
    fun geySmallDirection(@Field("direction_id") bigId: Int): Observable<SmallDirectionBean>
}