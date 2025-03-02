package com.stp.pacientes.api

data class Endereco(
    val numero: String,
    val cep: String,
    val rua: String,
    val bairro: String,
    val cidade: String,
    val estado: String
)

data class Ficha(
    val medicamentos: String,
    val historico: String,
    val alergias: String
)

data class Paciente(
    val cpf: String,
    val nome: String,
    val telefone: String,
    val email: String,
    val endereco: Endereco,
    val ficha: Ficha
)
