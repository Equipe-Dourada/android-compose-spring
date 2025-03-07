package com.stp.pacientes.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
fun PacienteDetailScreen(navController: NavController, cpf: String) {
    var paciente by remember { mutableStateOf<Paciente?>(null) }
    val context = LocalContext.current

    LaunchedEffect(cpf) {
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.getPacientes().enqueue(object : Callback<List<Paciente>> {
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Paciente", fontSize = 20.sp, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryColor)
            )
        }
    ) { paddingValues ->
        paciente?.let { p ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryColor.copy(alpha = 0.1f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Informações Pessoais", fontSize = 18.sp, color = PrimaryColor)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Nome: ${p.nome}", fontSize = 18.sp)
                        Text(text = "CPF: ${p.cpf}")
                        Text(text = "Telefone: ${p.telefone}")
                        Text(text = "Email: ${p.email}")
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
                ) {
                    ExtendedFloatingActionButton(
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                RetrofitClient.instance.deletePaciente(p.cpf).enqueue(object : Callback<Void> {
                                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            if (response.isSuccessful) {
                                                Toast.makeText(context, "Paciente excluído", Toast.LENGTH_SHORT).show()
                                                navController.popBackStack()
                                            } else {
                                                Toast.makeText(context, "Erro ao excluir", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        CoroutineScope(Dispatchers.Main).launch {
                                            Toast.makeText(context, "Erro: ${t.message}", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                })
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.error,
                        shape = RoundedCornerShape(16.dp),
                        icon = { Icon(Icons.Filled.Delete, contentDescription = "Excluir", tint = Color.White) },
                        text = { Text("Excluir", color = Color.White) }
                    )
                    ExtendedFloatingActionButton(
                        onClick = { navController.navigate("atualizarPaciente/${p.cpf}") },
                        shape = RoundedCornerShape(16.dp),
                        containerColor = PrimaryColor,
                        icon = { Icon(Icons.Filled.Edit, contentDescription = "Atualizar", tint = Color.White) },
                        text = { Text("Atualizar", color = Color.White) }
                    )
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
