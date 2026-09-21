package edu.unicauca.aplimovil.composelble4.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import edu.unicauca.aplimovil.composelble4.viewmodel.AppUiState

@Composable
fun SetupScreen(
    uiState: AppUiState,
    onComplete: () -> Unit,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 20.dp,
                vertical = 24.dp
            )
    ) {

        // ------------------------------------------------
        // MARCA
        // ------------------------------------------------

        Text(
            text = "TriageU",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )


        Spacer(
            modifier = Modifier.height(4.dp)
        )


        Text(
            text = "Prioriza tu tiempo.\nAvanza a tu ritmo.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        // ------------------------------------------------
        // TÍTULO
        // ------------------------------------------------

        Text(
            text = "Prepara tu semestre",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        Text(
            text =
                "Cuéntanos qué estás cursando y cuándo sueles tener tiempo para estudiar.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        // ------------------------------------------------
        // MATERIAS
        // ------------------------------------------------

        Text(
            text = "MIS MATERIAS",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        // En el prototipo mostramos las dos materias
        // que aparecen en el diseño de Figma.
        uiState.subjects
            .take(2)
            .forEach { subject ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),

                    shape =
                        RoundedCornerShape(12.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                MaterialTheme.colorScheme.surface
                        ),

                    border =
                        BorderStroke(
                            width = 1.dp,
                            color =
                                MaterialTheme.colorScheme.outline
                        ),

                    elevation =
                        CardDefaults.cardElevation(
                            defaultElevation = 0.dp
                        )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                horizontal = 16.dp
                            ),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Text(
                            text = subject.name,
                            style =
                                MaterialTheme.typography.bodyMedium,
                            color =
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                }


                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )
            }


        TextButton(
            onClick = {
                // Funcionalidad futura:
                // agregar una nueva materia.
            },

            modifier =
                Modifier.align(
                    Alignment.CenterHorizontally
                )
        ) {

            Text(
                text = "+ Agregar materia",
                style =
                    MaterialTheme.typography.bodyMedium,
                color =
                    MaterialTheme.colorScheme.primary
            )
        }


        Spacer(
            modifier = Modifier.height(22.dp)
        )


        // ------------------------------------------------
        // DISPONIBILIDAD
        // ------------------------------------------------

        Text(
            text =
                "¿Cuándo sueles tener tiempo para estudiar?",

            style =
                MaterialTheme.typography.titleMedium,

            color =
                MaterialTheme.colorScheme.onBackground
        )


        Spacer(
            modifier = Modifier.height(18.dp)
        )


        uiState.availability.forEach { availability ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 5.dp
                    ),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text = availability.day,
                    style =
                        MaterialTheme.typography.bodyMedium,
                    color =
                        MaterialTheme.colorScheme.onSurface
                )


                Text(
                    text = availability.hours,
                    style =
                        MaterialTheme.typography.bodyMedium,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }


        Spacer(
            modifier = Modifier.height(4.dp)
        )


        TextButton(
            onClick = {
                // Funcionalidad futura:
                // editar disponibilidad.
            },

            modifier =
                Modifier.align(
                    Alignment.CenterHorizontally
                )
        ) {

            Text(
                text = "Editar disponibilidad",
                style =
                    MaterialTheme.typography.bodySmall,
                color =
                    MaterialTheme.colorScheme.primary
            )
        }


        Spacer(
            modifier = Modifier.height(30.dp)
        )


        // ------------------------------------------------
        // CREAR PLAN
        // ------------------------------------------------

        Button(
            onClick = onComplete,

            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),

            shape =
                RoundedCornerShape(12.dp),

            colors =
                ButtonDefaults.buttonColors(

                    containerColor =
                        MaterialTheme.colorScheme.primary,

                    contentColor =
                        MaterialTheme.colorScheme.onPrimary
                )
        ) {

            Text(
                text = "Crear mi plan",
                style =
                    MaterialTheme.typography.labelLarge
            )
        }


        Spacer(
            modifier = Modifier.height(4.dp)
        )


        TextButton(
            onClick = onSkip,

            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Configurar después",
                style =
                    MaterialTheme.typography.bodySmall,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }


        Spacer(
            modifier = Modifier.height(16.dp)
        )
    }
}