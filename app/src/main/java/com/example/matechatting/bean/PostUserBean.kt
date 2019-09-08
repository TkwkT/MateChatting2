package com.example.matechatting.bean

import com.google.gson.annotations.SerializedName

data class PostUserBean(
    @SerializedName("city")
    var city: String = "",
    @SerializedName("company")
    var company: String = "",
    @SerializedName("directions")
    var directions: List<String>? = null,
    @SerializedName("email")
    var email: String = "",
    @SerializedName("graduation_year")
    var graduationYear: Int = 0,
    @SerializedName("job")
    var job: String = "",
    @SerializedName("major_name")
    var majorName: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("phone_number")
    var phoneNumber: Long = 0L,
    @SerializedName("qq_account")
    var qqAccount: Int = 0,
    @SerializedName("slogan")
    var slogan: String = "",
    @SerializedName("stu_id")
    var stuId: String = "",
    @SerializedName("wechat_account")
    var wechatAccount: String = "",
    @SerializedName("profile_photo")
    var headImage: String = "",
    @SerializedName("is_man")
    var is_man:Boolean = true,
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("in_school")
    var inSchool:Boolean = false
)