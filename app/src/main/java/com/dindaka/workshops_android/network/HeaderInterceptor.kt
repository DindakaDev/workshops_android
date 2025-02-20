package com.dindaka.workshops_android.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    private val contentTypeHeader = "Content-Type"
    private val contentTypeValue = "application/json"

    /**
     * Interceptor class for setting of the dynamic headers for every request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val shouldAddAuthHeaders = request.headers[ADD_AUTHORIZATION_HEADER] != ADD_AUTHORIZATION_VALUE
        request = request.newBuilder().apply {
            if (shouldAddAuthHeaders) {
                addHeader(contentTypeHeader, contentTypeValue)
            }
            removeHeader(ADD_AUTHORIZATION_HEADER)
        }.build()

        return chain.proceed(request)
    }

    companion object {
        const val ADD_AUTHORIZATION_HEADER = "isAuthorizable"
        const val ADD_AUTHORIZATION_VALUE = "false"
    }
}
