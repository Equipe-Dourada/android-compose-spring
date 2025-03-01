package com.stp.pacientes.api

import okhttp3.Interceptor
import okhttp3.Response

class CsrfInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        RetrofitClient.csrfToken?.let {
            request.addHeader("X-CSRF-TOKEN", it)
        }

        return chain.proceed(request.build())
    }
}
