package com.stp.pacientes.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("pacientes")
    fun login(): Call<Void>

    @GET("pacientes")
    fun getPacientes(): Call<List<Paciente>>

    @POST("pacientes")
    fun cadastrarPaciente(
        @Body paciente: Paciente
    ): Call<Paciente>

    @PUT("pacientes/{cpf}")
    fun updatePaciente(@Path("cpf") cpf: String, @Body paciente: Paciente): Call<Void>

    @DELETE("pacientes/{cpf}")
    fun deletePaciente(@Path("cpf") cpf: String): Call<Void>
}
