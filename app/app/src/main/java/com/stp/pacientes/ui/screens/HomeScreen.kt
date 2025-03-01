package com.stp.pacientes.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
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
fun HomeScreen(navController: NavController) {
    var pacientes by remember { mutableStateOf<List<Paciente>>(emptyList()) }
    val csrfToken = RetrofitClient.csrfToken

    LaunchedEffect(csrfToken) {
        if (csrfToken != null) {
            CoroutineScope(Dispatchers.IO).launch {
                RetrofitClient.instance.getPacientes(csrfToken).enqueue(object : Callback<List<Paciente>> {
                    override fun onResponse(call: Call<List<Paciente>>, response: Response<List<Paciente>>) {
                        if (response.isSuccessful) {
                            pacientes = response.body() ?: emptyList()
                        } else {
                            Log.e("HomeScreen", "Erro na resposta: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<List<Paciente>>, t: Throwable) {
                        Log.e("HomeScreen", "Erro na requisição: ${t.message}")
                    }
                })
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Lista de Pacientes", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        pacientes.forEach { paciente ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("pacienteDetail/${paciente.cpf}") }
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Nome: ${paciente.nome}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "CPF: ${paciente.cpf}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

