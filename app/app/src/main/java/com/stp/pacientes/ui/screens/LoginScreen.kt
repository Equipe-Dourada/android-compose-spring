package com.stp.pacientes.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.stp.pacientes.api.RetrofitClient
import com.stp.pacientes.ui.theme.PacientesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sistema de Pacientes", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val authHeader = RetrofitClient.getAuthHeader(username, password)
                CoroutineScope(Dispatchers.Main).launch {
                    RetrofitClient.instance.login(authHeader).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            CoroutineScope(Dispatchers.Main).launch {
                                if (response.isSuccessful) {
                                    val csrfToken = response.headers()["X-CSRF-TOKEN"]
                                    if (csrfToken != null) {
                                        RetrofitClient.csrfToken = csrfToken
                                    }
                                    Toast.makeText(context, "Login bem-sucedido", Toast.LENGTH_SHORT).show()
                                    navController.navigate("home")
                                } else {
                                    Toast.makeText(context, "Falha no login", Toast.LENGTH_LONG).show()
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    PacientesTheme {
        val navController = rememberNavController()
        LoginScreen(navController)
    }
}
