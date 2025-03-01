package com.stp.pacientes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stp.pacientes.api.RetrofitClient
import com.stp.pacientes.ui.theme.PacientesTheme

@Composable
fun HomeScreen() {
    val csrfToken = RetrofitClient.csrfToken

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Lista de Pacientes", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Token CSRF:")
        Text(text = csrfToken ?: "Nenhum token disponível")
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PacientesTheme {
        HomeScreen()
    }
}
