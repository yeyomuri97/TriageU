package edu.unicauca.aplimovil.composelble4.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Insights
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    data object Setup : Screen("setup", "Configuración")
    data object Hoy : Screen("hoy", "Hoy", Icons.Default.Home)
    data object MiRuta : Screen("mi_ruta", "Mi Ruta", Icons.Default.Route)
    data object Progreso : Screen("progreso", "Progreso", Icons.Default.Insights)
    data object Perfil : Screen("perfil", "Perfil", Icons.Default.Person)
    data object NuevaActividad : Screen("nueva_actividad", "Nueva actividad", Icons.Default.Add)
}

val bottomNavItems = listOf(
    Screen.Hoy,
    Screen.MiRuta,
    Screen.Progreso,
    Screen.Perfil
)
