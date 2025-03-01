package com.stp.pacientes.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("pacientes")
    fun login(@Header("Authorization") authHeader: String): Call<Void>
}
