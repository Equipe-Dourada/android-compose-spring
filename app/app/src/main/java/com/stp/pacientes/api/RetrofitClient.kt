package com.stp.pacientes.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.26:8080/api/"

    private lateinit var username: String
    private lateinit var password: String

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .authenticator(BasicAuthenticator(username, password))
            .build()
    }

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }

    fun setCredentials(username: String, password: String) {
        this.username = username
        this.password = password
    }
}
