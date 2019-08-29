package com.example.matechatting.utils

import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType

object PinyinUtil {

    fun getPinyin(str:String):String{
        val format = HanyuPinyinOutputFormat()
        format.caseType = HanyuPinyinCaseType.UPPERCASE
        format.toneType = HanyuPinyinToneType.WITHOUT_TONE
        val sb = StringBuilder()
        val charArray = str.toCharArray()
        for (c:Char in charArray){
            if (' ' == c){
                continue
            }
            if(c.toInt() >= -127 && c.toInt() <= 128){
                sb.append(c)
            }else{
                val s:String = PinyinHelper.toHanyuPinyinStringArray(c,format)[0]
                sb.append(s)
            }
        }
        return sb.toString()
    }

    fun getFirstPinyin(str:String):String{
        return getPinyin(str).substring(0,1)
    }
}