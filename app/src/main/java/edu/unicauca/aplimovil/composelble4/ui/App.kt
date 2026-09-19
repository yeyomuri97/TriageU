package edu.unicauca.aplimovil.composelble4.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.unicauca.aplimovil.composelble4.ui.navigation.Screen
import edu.unicauca.aplimovil.composelble4.ui.navigation.bottomNavItems
import edu.unicauca.aplimovil.composelble4.ui.screens.HoyScreen
import edu.unicauca.aplimovil.composelble4.ui.screens.MiRutaScreen
import edu.unicauca.aplimovil.composelble4.ui.screens.NuevaActividadScreen
import edu.unicauca.aplimovil.composelble4.ui.screens.PerfilScreen
import edu.unicauca.aplimovil.composelble4.ui.screens.ProgresoScreen
import edu.unicauca.aplimovil.composelble4.ui.screens.SetupScreen
import edu.unicauca.aplimovil.composelble4.ui.theme.SageGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.SurfaceWhite
import edu.unicauca.aplimovil.composelble4.ui.theme.TextSecondary
import edu.unicauca.aplimovil.composelble4.viewmodel.AppViewModel

@Composable
fun UnicaucaApp(
    viewModel: AppViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = bottomNavItems.any { item ->
        currentDestination?.hierarchy?.any { it.route == item.route } == true
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = SurfaceWhite,
                    tonalElevation = 0.dp
                ) {
                    bottomNavItems.forEach { screen ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true
                        NavigationBarItem(
                            icon = {
                                screen.icon?.let {
                                    Icon(
                                        imageVector = it,
                                        contentDescription = screen.title,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    fontSize = 11.sp
                                )
                            },
                            selected = selected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = SageGreen,
                                selectedTextColor = SageGreen,
                                unselectedIconColor = TextSecondary,
                                unselectedTextColor = TextSecondary,
                                indicatorColor = SurfaceWhite
                            )
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            if (showBottomBar) {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.NuevaActividad.route) },
                    containerColor = SageGreen,
                    contentColor = SurfaceWhite,
                    shape = CircleShape,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Nueva actividad",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = if (uiState.isSetupComplete) Screen.Hoy.route else Screen.Setup.route,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(Screen.Setup.route) {
                    SetupScreen(
                        uiState = uiState,
                        onComplete = {
                            viewModel.completeSetup()
                            navController.navigate(Screen.Hoy.route) {
                                popUpTo(Screen.Setup.route) { inclusive = true }
                            }
                        },
                        onSkip = {
                            viewModel.skipSetup()
                            navController.navigate(Screen.Hoy.route) {
                                popUpTo(Screen.Setup.route) { inclusive = true }
                            }
                        }
                    )
                }
                composable(Screen.Hoy.route) {
                    HoyScreen(
                        uiState = uiState,
                        onToggleComplete = { viewModel.toggleActivityCompleted(it) }
                    )
                }
                composable(Screen.MiRuta.route) {
                    MiRutaScreen(uiState = uiState)
                }
                composable(Screen.Progreso.route) {
                    ProgresoScreen(uiState = uiState)
                }
                composable(Screen.Perfil.route) {
                    PerfilScreen(uiState = uiState)
                }
                composable(Screen.NuevaActividad.route) {
                    NuevaActividadScreen(
                        uiState = uiState,
                        onTitleChange = { viewModel.updateNewActivityTitle(it) },
                        onSubjectChange = { viewModel.updateNewActivitySubject(it) },
                        onDurationChange = { viewModel.updateNewActivityDuration(it) },
                        onPriorityChange = { viewModel.updateNewActivityPriority(it) },
                        onSave = { viewModel.saveNewActivity() },
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
