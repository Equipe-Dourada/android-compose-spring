package com.stp.pacientes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stp.pacientes.ui.screens.AtualizarPacienteScreen
import com.stp.pacientes.ui.screens.CadastroScreen
import com.stp.pacientes.ui.screens.HomeScreen
import com.stp.pacientes.ui.screens.LoginScreen
import com.stp.pacientes.ui.screens.PacienteDetailScreen
import com.stp.pacientes.ui.theme.PacientesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PacientesTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("addPaciente") { }
        composable("pacienteDetail/{cpf}") { backStackEntry ->
            val cpf = backStackEntry.arguments?.getString("cpf") ?: ""
            PacienteDetailScreen(navController, cpf)
        }
        composable("cadastro") { CadastroScreen(navController) }
        composable("atualizarPaciente/{cpf}") { backStackEntry ->
            val cpf = backStackEntry.arguments?.getString("cpf") ?: ""
            AtualizarPacienteScreen(navController, cpf)
        }
    }
}
