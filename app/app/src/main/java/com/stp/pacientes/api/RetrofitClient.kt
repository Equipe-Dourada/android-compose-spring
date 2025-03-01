package com.stp.pacientes.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Base64

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    var csrfToken: String? = null

    private val client = OkHttpClient.Builder()
        .addInterceptor(CsrfInterceptor())
        .build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }

    fun getAuthHeader(username: String, password: String): String {
        val credentials = "$username:$password"
        return "Basic " + Base64.getEncoder().encodeToString(credentials.toByteArray())
    }
}
