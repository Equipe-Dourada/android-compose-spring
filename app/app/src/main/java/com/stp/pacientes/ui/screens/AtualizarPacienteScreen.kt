package com.stp.pacientes.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stp.pacientes.api.Endereco
import com.stp.pacientes.api.Ficha
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
fun AtualizarPacienteScreen(navController: NavController, cpf: String) {
    var paciente by remember { mutableStateOf<Paciente?>(null) }
    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var rua by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var alergias by remember { mutableStateOf("") }
    var historico by remember { mutableStateOf("") }
    var medicamentos by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current

    LaunchedEffect(cpf) {
        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.getPacientes().enqueue(object : Callback<List<Paciente>> {
                override fun onResponse(call: Call<List<Paciente>>, response: Response<List<Paciente>>) {
                    if (response.isSuccessful) {
                        paciente = response.body()?.find { it.cpf == cpf }
                        paciente?.let {
                            nome = it.nome
                            telefone = it.telefone
                            email = it.email
                            rua = it.endereco.rua
                            numero = it.endereco.numero
                            bairro = it.endereco.bairro
                            cidade = it.endereco.cidade
                            estado = it.endereco.estado
                            cep = it.endereco.cep
                            alergias = it.ficha.alergias
                            historico = it.ficha.historico
                            medicamentos = it.ficha.medicamentos
                        }
                    } else {
                        Log.e("AtualizarPaciente", "Erro na resposta: ${response.errorBody()?.string()}")
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(context, "Erro ao carregar dados", Toast.LENGTH_SHORT).show()
                        }
                    }
                    isLoading = false
                }

                override fun onFailure(call: Call<List<Paciente>>, t: Throwable) {
                    Log.e("AtualizarPaciente", "Erro na requisição: ${t.message}")
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(context, "Erro na requisição", Toast.LENGTH_SHORT).show()
                    }
                    isLoading = false
                }
            })
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Atualizar Paciente") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val endereco = Endereco(rua, numero, bairro, cidade, estado, cep)
                        val ficha = Ficha(alergias, historico, medicamentos)
                        val pacienteAtualizado = Paciente(cpf, nome, telefone, email, endereco, ficha)

                        RetrofitClient.instance.updatePaciente(cpf, pacienteAtualizado).enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    if (response.isSuccessful) {
                                        Toast.makeText(context, "Paciente atualizado", Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    } else {
                                        Toast.makeText(context, "Erro ao atualizar", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    Toast.makeText(context, "Erro na requisição", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Salvar")
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Informações Pessoais", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = telefone, onValueChange = { telefone = it }, label = { Text("Telefone") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
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
                        OutlinedTextField(value = rua, onValueChange = { rua = it }, label = { Text("Rua") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = numero, onValueChange = { numero = it }, label = { Text("Número") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = bairro, onValueChange = { bairro = it }, label = { Text("Bairro") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = cidade, onValueChange = { cidade = it }, label = { Text("Cidade") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = estado, onValueChange = { estado = it }, label = { Text("Estado") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = cep, onValueChange = { cep = it }, label = { Text("CEP") }, modifier = Modifier.fillMaxWidth())
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
                        OutlinedTextField(value = alergias, onValueChange = { alergias = it }, label = { Text("Alergias") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = historico, onValueChange = { historico = it }, label = { Text("Histórico") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = medicamentos, onValueChange = { medicamentos = it }, label = { Text("Medicamentos") }, modifier = Modifier.fillMaxWidth())
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}
