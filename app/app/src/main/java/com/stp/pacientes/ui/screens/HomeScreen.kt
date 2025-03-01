package com.stp.pacientes.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stp.pacientes.api.Paciente
import com.stp.pacientes.api.RetrofitClient
import com.stp.pacientes.ui.theme.PacientesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun HomeScreen() {
    var pacientes by remember { mutableStateOf<List<Paciente>>(emptyList()) }
    val csrfToken = RetrofitClient.csrfToken

    LaunchedEffect(csrfToken) {
        if (csrfToken != null) {
            CoroutineScope(Dispatchers.IO).launch {
                RetrofitClient.instance.getPacientes(csrfToken).enqueue(object : Callback<List<Paciente>> {
                    override fun onResponse(call: Call<List<Paciente>>, response: Response<List<Paciente>>) {
                        if (response.isSuccessful) {
                            pacientes = response.body() ?: emptyList()
                            Log.d("HomeScreen", "Pacientes recebidos: $pacientes")
                        } else {
                            Log.e("HomeScreen", "Erro na resposta: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<List<Paciente>>, t: Throwable) {
                    }
                })
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Lista de Pacientes", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        pacientes.forEach { paciente ->
            Text(text = "Nome: ${paciente.nome}")
            Text(text = "CPF: ${paciente.cpf}")
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PacientesTheme {
        HomeScreen()
    }
}
