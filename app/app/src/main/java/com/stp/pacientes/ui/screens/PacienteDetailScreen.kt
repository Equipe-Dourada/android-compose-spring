package com.stp.pacientes.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stp.pacientes.api.Paciente
import com.stp.pacientes.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun PacienteDetailScreen(navController: NavController, cpf: String) {
    var paciente by remember { mutableStateOf<Paciente?>(null) }
    val csrfToken = RetrofitClient.csrfToken

    LaunchedEffect(cpf, csrfToken) {
        if (csrfToken != null) {
            CoroutineScope(Dispatchers.IO).launch {
                RetrofitClient.instance.getPacientes(csrfToken).enqueue(object : Callback<List<Paciente>> {
                    override fun onResponse(call: Call<List<Paciente>>, response: Response<List<Paciente>>) {
                        if (response.isSuccessful) {
                            paciente = response.body()?.find { it.cpf == cpf }
                        } else {
                            Log.e("PacienteDetail", "Erro na resposta: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<List<Paciente>>, t: Throwable) {
                        Log.e("PacienteDetail", "Erro na requisição: ${t.message}")
                    }
                })
            }
        }
    }

    paciente?.let { p ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Detalhes do Paciente", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Nome: ${p.nome}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "CPF: ${p.cpf}")
            Text(text = "Telefone: ${p.telefone}")
            Text(text = "Email: ${p.email}")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Endereço:", style = MaterialTheme.typography.titleLarge)
            Text(text = "${p.endereco.rua}, ${p.endereco.numero}")
            Text(text = "${p.endereco.bairro}, ${p.endereco.cidade} - ${p.endereco.estado}")
            Text(text = "CEP: ${p.endereco.cep}")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Histórico Médico:", style = MaterialTheme.typography.titleLarge)
            Text(text = "Alergias: ${p.ficha.alergias}")
            Text(text = "Histórico: ${p.ficha.historico}")
            Text(text = "Medicamentos: ${p.ficha.medicamentos}")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Voltar")
            }
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
