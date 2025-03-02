package com.stp.pacientes.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
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
fun CadastroScreen(navController: NavController) {
    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var rua by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var historico by remember { mutableStateOf("") }
    var alergias by remember { mutableStateOf("") }
    var medicamentos by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    fun cadastrarPaciente() {
        isLoading = true
        val novoPaciente = Paciente(
            cpf = cpf,
            nome = nome,
            telefone = telefone,
            email = email,
            endereco = Endereco(numero, cep, rua, bairro, cidade, estado),
            ficha = Ficha(medicamentos, historico, alergias)
        )

        CoroutineScope(Dispatchers.IO).launch {
            RetrofitClient.instance.cadastrarPaciente(novoPaciente)
                .enqueue(object : Callback<Paciente> {
                    override fun onResponse(call: Call<Paciente>, response: Response<Paciente>) {
                        isLoading = false
                        if (response.isSuccessful) {
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(context, "Paciente cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }

                        } else {
                            Log.e("CadastroScreen", "Erro ao cadastrar: ${response.errorBody()?.string()}")
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(context, "Erro ao cadastrar", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<Paciente>, t: Throwable) {
                        isLoading = false
                        Log.e("CadastroScreen", "Erro na requisição: ${t.message}")
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(context, "Erro na requisição", Toast.LENGTH_LONG).show()
                        }
                    }
                })
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastrar Paciente") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = { cadastrarPaciente() },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Cadastrar")
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Informações Pessoais", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = cpf, onValueChange = { cpf = it }, label = { Text("CPF") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                    OutlinedTextField(value = telefone, onValueChange = { telefone = it }, label = { Text("Telefone") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
                    OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
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
                    OutlinedTextField(value = numero, onValueChange = { numero = it }, label = { Text("Número") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                    OutlinedTextField(value = bairro, onValueChange = { bairro = it }, label = { Text("Bairro") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = cidade, onValueChange = { cidade = it }, label = { Text("Cidade") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = estado, onValueChange = { estado = it }, label = { Text("Estado") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = cep, onValueChange = { cep = it }, label = { Text("CEP") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
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
                    OutlinedTextField(value = historico, onValueChange = { historico = it }, label = { Text("Histórico") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = alergias, onValueChange = { alergias = it }, label = { Text("Alergias") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = medicamentos, onValueChange = { medicamentos = it }, label = { Text("Medicamentos") }, modifier = Modifier.fillMaxWidth())
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
