package edu.unicauca.aplimovil.composelble4.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityHighBg
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityHighText
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityLowBg
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityLowText
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityMedBg
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityMedText
import edu.unicauca.aplimovil.composelble4.viewmodel.ActivityItem
import edu.unicauca.aplimovil.composelble4.viewmodel.AppUiState
import edu.unicauca.aplimovil.composelble4.viewmodel.Priority

@Composable
fun HoyScreen(
    uiState: AppUiState,
    onToggleComplete: (Int) -> Unit,
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
        // ENCABEZADO
        // ------------------------------------------------

        Text(
            text = "Buenos días!",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        Text(
            text = "Lunes, 31 de agosto",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        // ------------------------------------------------
        // RESUMEN DEL DÍA
        // ------------------------------------------------

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color =
                        MaterialTheme.colorScheme.primaryContainer,
                    shape =
                        RoundedCornerShape(14.dp)
                )
                .padding(
                    horizontal = 14.dp,
                    vertical = 10.dp
                )
        ) {

            Text(
                text = "Hoy tienes:",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Text(
                text = "${uiState.availableTime} disponibles",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "${uiState.plannedTime} planificadas",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        // ------------------------------------------------
        // PLAN RECOMENDADO
        // ------------------------------------------------

        Text(
            text = "Tu plan recomendado",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )


        Spacer(
            modifier = Modifier.height(10.dp)
        )


        uiState.todayActivities.forEach { activity ->

            RecommendedActivityCard(
                activity = activity,
                onToggleComplete = {
                    onToggleComplete(activity.id)
                }
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )
        }


        // ------------------------------------------------
        // AJUSTAR PLAN
        // ------------------------------------------------

        OutlinedButton(
            onClick = {
                // Acción futura para ajustar el plan.
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(44.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
        ) {

            Text(
                text = "Ajustar mi plan",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }


        Spacer(
            modifier = Modifier.height(24.dp)
        )
    }
}


@Composable
private fun RecommendedActivityCard(
    activity: ActivityItem,
    onToggleComplete: () -> Unit
) {

    val chipBackground =
        when (activity.priority) {

            Priority.HIGH ->
                PriorityHighBg

            Priority.MEDIUM ->
                PriorityMedBg

            Priority.LOW ->
                PriorityLowBg
        }


    val chipTextColor =
        when (activity.priority) {

            Priority.HIGH ->
                PriorityHighText

            Priority.MEDIUM ->
                PriorityMedText

            Priority.LOW ->
                PriorityLowText
        }


    val priorityText =
        when (activity.priority) {

            Priority.HIGH ->
                "↑ PRIORIDAD ALTA"

            Priority.MEDIUM ->
                "— PRIORIDAD MEDIA"

            Priority.LOW ->
                "↓ PRIORIDAD BAJA"
        }


    val preparationText =
        when (activity.preparation) {

            1, 2 ->
                "baja"

            3 ->
                "media"

            else ->
                "alta"
        }


    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 12.dp
            )
        ) {

            // --------------------------------------------
            // PRIORIDAD + BOTÓN DE COMPLETADO
            // --------------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .background(
                            color = chipBackground,
                            shape =
                                RoundedCornerShape(8.dp)
                        )
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                ) {

                    Text(
                        text = priorityText,
                        style =
                            MaterialTheme.typography.labelSmall,
                        color = chipTextColor
                    )
                }


                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .border(
                            width = 1.5.dp,
                            color =
                                if (activity.completed) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
                            shape = CircleShape
                        )
                        .background(
                            color =
                                if (activity.completed) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.surface
                                },
                            shape = CircleShape
                        )
                        .clickable {
                            onToggleComplete()
                        },
                    contentAlignment = Alignment.Center
                ) {

                    if (activity.completed) {

                        Icon(
                            imageVector =
                                Icons.Default.Check,
                            contentDescription =
                                "Actividad realizada",
                            tint =
                                MaterialTheme.colorScheme.onPrimary,
                            modifier =
                                Modifier.size(15.dp)
                        )
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            // --------------------------------------------
            // TÍTULO
            // --------------------------------------------

            Text(
                text = activity.title,
                style = MaterialTheme.typography.titleMedium,
                color =
                    if (activity.completed) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                textDecoration =
                    if (activity.completed) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    }
            )


            Spacer(
                modifier = Modifier.height(2.dp)
            )


            // --------------------------------------------
            // DURACIÓN
            // --------------------------------------------

            Text(
                text =
                    "${activity.durationMin} min recomendados",
                style =
                    MaterialTheme.typography.bodySmall,
                color =
                    MaterialTheme.colorScheme.primary
            )


            Spacer(
                modifier = Modifier.height(4.dp)
            )


            // --------------------------------------------
            // FECHA + PESO
            // --------------------------------------------

            val weightText =
                activity.weightPercent?.let {
                    " · $it % de la nota"
                } ?: ""


            Text(
                text =
                    "${activity.dueText}$weightText",
                style =
                    MaterialTheme.typography.bodySmall,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )


            // --------------------------------------------
            // PREPARACIÓN
            // --------------------------------------------

            Text(
                text =
                    "Preparación: $preparationText",
                style =
                    MaterialTheme.typography.bodySmall,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}