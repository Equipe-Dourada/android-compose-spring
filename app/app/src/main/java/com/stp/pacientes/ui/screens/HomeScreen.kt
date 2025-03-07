package com.stp.pacientes.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stp.pacientes.api.Paciente
import com.stp.pacientes.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val PrimaryColor = Color(0xFF692E96)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var pacientes by remember { mutableStateOf<List<Paciente>>(emptyList()) }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.getPacientes().enqueue(object : Callback<List<Paciente>> {
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pacientes", fontSize = 20.sp, color = Color.White) },
                actions = {
                    IconButton(onClick = { navController.navigate("login") }) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Sair", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryColor)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Adicionar", color = Color.White) },
                onClick = { navController.navigate("cadastro") },
                shape = RoundedCornerShape(16.dp),
                containerColor = PrimaryColor,
                icon = { Icon(Icons.Filled.PersonAdd, contentDescription = "Adicionar Paciente", tint = Color.White) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(pacientes) { paciente ->
                    PacienteItem(paciente, navController)
                }
            }
        }
    }
}

@Composable
fun PacienteItem(paciente: Paciente, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("pacienteDetail/${paciente.cpf}") }
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryColor.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = paciente.nome, fontSize = 18.sp, color = PrimaryColor)
            Text(text = "CPF: ${paciente.cpf}", fontSize = 14.sp)
        }
    }
}