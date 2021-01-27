package com.song.common.extentions.okhttp

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * 可用于统一处理的拦截器，比如解密
 */
class StringResponseInterceptor(private val handler: (String) -> String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.isSuccessful.not()) return response
        val body = response.body ?: return response
        val string = body.string()
        val finalStr = handler(string)
        return response.newBuilder().body(finalStr.toResponseBody()).build()
    }
}