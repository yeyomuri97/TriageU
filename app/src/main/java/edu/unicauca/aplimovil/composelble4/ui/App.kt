package edu.unicauca.aplimovil.composelble4.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import edu.unicauca.aplimovil.composelble4.ui.screens.AcercaScreen
import edu.unicauca.aplimovil.composelble4.ui.screens.HoyScreen
import edu.unicauca.aplimovil.composelble4.ui.screens.MiRutaScreen
import edu.unicauca.aplimovil.composelble4.ui.screens.NuevaActividadScreen
import edu.unicauca.aplimovil.composelble4.ui.screens.PerfilScreen
import edu.unicauca.aplimovil.composelble4.ui.screens.ProgresoScreen
import edu.unicauca.aplimovil.composelble4.ui.screens.SetupScreen
import edu.unicauca.aplimovil.composelble4.viewmodel.AppViewModel

@Composable
fun UnicaucaApp(
    viewModel: AppViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val navController = rememberNavController()

    val navBackStackEntry by
    navController.currentBackStackEntryAsState()

    val currentDestination =
        navBackStackEntry?.destination

    val showBottomBar =
        bottomNavItems.any { item ->
            currentDestination
                ?.hierarchy
                ?.any {
                    it.route == item.route
                } == true
        }

    Scaffold(
        containerColor =
            MaterialTheme.colorScheme.background,

        bottomBar = {

            if (showBottomBar) {

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    NavigationBar(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor =
                            MaterialTheme.colorScheme.surface,
                        tonalElevation = 0.dp
                    ) {

                        // HOY
                        val hoy = bottomNavItems[0]

                        NavigationBarItem(
                            selected =
                                currentDestination
                                    ?.hierarchy
                                    ?.any {
                                        it.route == hoy.route
                                    } == true,

                            onClick = {
                                navController.navigate(hoy.route) {
                                    popUpTo(
                                        navController
                                            .graph
                                            .findStartDestination()
                                            .id
                                    ) {
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },

                            icon = {
                                hoy.icon?.let { icon ->
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = hoy.title,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            },

                            label = {
                                Text(
                                    text = hoy.title,
                                    style =
                                        MaterialTheme.typography.labelSmall
                                )
                            },

                            colors = navigationItemColors()
                        )

                        // MI RUTA
                        val ruta = bottomNavItems[1]

                        NavigationBarItem(
                            selected =
                                currentDestination
                                    ?.hierarchy
                                    ?.any {
                                        it.route == ruta.route
                                    } == true,

                            onClick = {
                                navController.navigate(ruta.route) {
                                    popUpTo(
                                        navController
                                            .graph
                                            .findStartDestination()
                                            .id
                                    ) {
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },

                            icon = {
                                ruta.icon?.let { icon ->
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = ruta.title,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            },

                            label = {
                                Text(
                                    text = ruta.title,
                                    style =
                                        MaterialTheme.typography.labelSmall
                                )
                            },

                            colors = navigationItemColors()
                        )

                        // ESPACIO CENTRAL PARA EL +
                        Spacer(
                            modifier = Modifier.width(72.dp)
                        )

                        // PROGRESO
                        val progreso = bottomNavItems[2]

                        NavigationBarItem(
                            selected =
                                currentDestination
                                    ?.hierarchy
                                    ?.any {
                                        it.route == progreso.route
                                    } == true,

                            onClick = {
                                navController.navigate(progreso.route) {
                                    popUpTo(
                                        navController
                                            .graph
                                            .findStartDestination()
                                            .id
                                    ) {
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },

                            icon = {
                                progreso.icon?.let { icon ->
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = progreso.title,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            },

                            label = {
                                Text(
                                    text = progreso.title,
                                    style =
                                        MaterialTheme.typography.labelSmall
                                )
                            },

                            colors = navigationItemColors()
                        )

                        // PERFIL
                        val perfil = bottomNavItems[3]

                        NavigationBarItem(
                            selected =
                                currentDestination
                                    ?.hierarchy
                                    ?.any {
                                        it.route == perfil.route
                                    } == true,

                            onClick = {
                                navController.navigate(perfil.route) {
                                    popUpTo(
                                        navController
                                            .graph
                                            .findStartDestination()
                                            .id
                                    ) {
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },

                            icon = {
                                perfil.icon?.let { icon ->
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = perfil.title,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            },

                            label = {
                                Text(
                                    text = perfil.title,
                                    style =
                                        MaterialTheme.typography.labelSmall
                                )
                            },

                            colors = navigationItemColors()
                        )
                    }

                    // BOTÓN + SOBRESALIENDO DE LA BARRA
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(
                                Screen.NuevaActividad.route
                            )
                        },

                        containerColor =
                            MaterialTheme.colorScheme.primary,

                        contentColor =
                            MaterialTheme.colorScheme.onPrimary,

                        shape = CircleShape,

                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset(y = (-18).dp)
                            .size(56.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Nueva actividad",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }

    ) { innerPadding ->

        Box(
            modifier = Modifier.padding(innerPadding)
        ) {

            NavHost(
                navController = navController,

                startDestination =
                    if (uiState.isSetupComplete) {
                        Screen.Hoy.route
                    } else {
                        Screen.Setup.route
                    },

                modifier = Modifier.fillMaxSize()
            ) {

                composable(Screen.Setup.route) {

                    SetupScreen(
                        uiState = uiState,

                        onComplete = {
                            viewModel.completeSetup()

                            navController.navigate(
                                Screen.Hoy.route
                            ) {
                                popUpTo(
                                    Screen.Setup.route
                                ) {
                                    inclusive = true
                                }
                            }
                        },

                        onSkip = {
                            viewModel.skipSetup()

                            navController.navigate(
                                Screen.Hoy.route
                            ) {
                                popUpTo(
                                    Screen.Setup.route
                                ) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }

                composable(Screen.Hoy.route) {

                    HoyScreen(
                        uiState = uiState,

                        onToggleComplete = {
                            viewModel.toggleActivityComplete(it)
                        }
                    )
                }

                composable(Screen.MiRuta.route) {

                    MiRutaScreen(
                        uiState = uiState
                    )
                }

                composable(Screen.Progreso.route) {

                    ProgresoScreen(
                        uiState = uiState
                    )
                }

                composable(Screen.Perfil.route) {

                    PerfilScreen(
                        uiState = uiState,

                        onAboutClick = {
                            navController.navigate(
                                Screen.Acerca.route
                            )
                        }
                    )
                }

                composable(Screen.Acerca.route) {

                    AcercaScreen(
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(Screen.NuevaActividad.route) {

                    NuevaActividadScreen(

                        uiState = uiState,

                        onTitleChange = {
                            viewModel.updateNewActivityTitle(it)
                        },

                        onSubjectChange = {
                            viewModel.updateNewActivitySubject(it)
                        },

                        onTypeChange = {
                            viewModel.updateNewActivityType(it)
                        },

                        onDateChange = {
                            viewModel.updateNewActivityDate(it)
                        },

                        onWeightChange = {
                            viewModel.updateNewActivityWeight(it)
                        },

                        onPreparationChange = {
                            viewModel.updateNewActivityPreparation(it)
                        },

                        onEstimatedTimeChange = {
                            viewModel.updateNewActivityEstimatedTime(it)
                        },

                        onSave = {
                            viewModel.saveNewActivity()
                            navController.popBackStack()
                        },

                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun navigationItemColors() =
    NavigationBarItemDefaults.colors(

        selectedIconColor =
            MaterialTheme.colorScheme.primary,

        selectedTextColor =
            MaterialTheme.colorScheme.primary,

        unselectedIconColor =
            MaterialTheme.colorScheme.onSurfaceVariant,

        unselectedTextColor =
            MaterialTheme.colorScheme.onSurfaceVariant,

        indicatorColor =
            MaterialTheme.colorScheme.surface
    )