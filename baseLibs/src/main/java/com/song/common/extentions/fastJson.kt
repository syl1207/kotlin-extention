package com.song.common.extentions

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject

fun String.toJsonObjectOrNull(): JSONObject? {
    return try {
        JSON.parseObject(this)
    } catch (e: Exception) {
        null
    }
}

fun String.toJsonArrayOrNull(): JSONArray? {
    return try {
        JSON.parseArray(this)
    } catch (e: Exception) {
        null
    }
}

inline fun <reified T> String.toObjectListOrNull(): List<T>? {
    return try {
        JSON.parseArray(this, T::class.java)
    } catch (e: Exception) {
        null
    }
}

fun JSONObject.string(string: String): String {
    return getString(string) ?: ""
}

inline fun <reified T> JSONObject.toObjectOrNull(): T? {
    return try {
        JSON.parseObject(toJSONString(), T::class.java)
    } catch (e: Exception) {
        null
    }
}

inline fun <reified T> JSONArray.toObjectListOrNull(): List<T>? {
    return try {
        JSON.parseArray(toJSONString(), T::class.java)
    } catch (e: Exception) {
        null
    }
}


/**
 *把jsonArray先转成Bridge类型的列表后转换为Target类型list,适合本地字段跟服务器字段不一致的情形
 */

inline fun <reified Bridge, reified Target> JSONArray.parse(convert: (Bridge) -> Target): List<Target> {
    val list = mutableListOf<Target>()
    forEach {
        list.add(convert(JSON.parseObject(it.toString(), Bridge::class.java)))
    }
    return list
}
