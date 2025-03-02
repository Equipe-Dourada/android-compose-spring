package com.stp.pacientes.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
                title = { Text("Detalhes do Paciente") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
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
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Informações Pessoais", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Nome: ${p.nome}", style = MaterialTheme.typography.bodyLarge)
                        Text(text = "CPF: ${p.cpf}")
                        Text(text = "Telefone: ${p.telefone}")
                        Text(text = "Email: ${p.email}")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Endereço", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "${p.endereco.rua}, ${p.endereco.numero}")
                        Text(text = "${p.endereco.bairro}, ${p.endereco.cidade} - ${p.endereco.estado}")
                        Text(text = "CEP: ${p.endereco.cep}")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Histórico Médico", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Alergias: ${p.ficha.alergias}")
                        Text(text = "Histórico: ${p.ficha.historico}")
                        Text(text = "Medicamentos: ${p.ficha.medicamentos}")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
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
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(Icons.Filled.Delete, contentDescription = "Excluir")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Excluir")
                    }
                    Button(
                        onClick = { navController.navigate("atualizarPaciente/${p.cpf}") },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = "Atualizar")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Atualizar")
                    }
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
