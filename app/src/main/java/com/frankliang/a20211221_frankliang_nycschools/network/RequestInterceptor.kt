package com.frankliang.a20211221_frankliang_nycschools.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    companion object {
        const val HEADER_KEY = "X-App-Token"
        const val HEADER_VALUE = "9OiZn1bECIZlroUAUdyVovSfA"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader(HEADER_KEY, HEADER_VALUE).build()
        return chain.proceed(request)
    }
}