package com.stp.pacientes.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("csrf")
    fun login(): Call<Void>

    @GET("pacientes")
    fun getPacientes(@Header("X-CSRF-TOKEN") csrfToken: String): Call<List<Paciente>>
}
