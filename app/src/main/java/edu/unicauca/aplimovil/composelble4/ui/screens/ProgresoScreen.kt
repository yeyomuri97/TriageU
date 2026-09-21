package edu.unicauca.aplimovil.composelble4.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.unicauca.aplimovil.composelble4.ui.theme.SageGreen
import edu.unicauca.aplimovil.composelble4.viewmodel.AppUiState
import edu.unicauca.aplimovil.composelble4.viewmodel.ProgressDay
import edu.unicauca.aplimovil.composelble4.viewmodel.WeekBar

@Composable
fun ProgresoScreen(
    uiState: AppUiState,
    modifier: Modifier = Modifier
) {

    val progress =
        if (uiState.progressBlocksTotal > 0) {
            uiState.progressBlocksDone.toFloat() /
                    uiState.progressBlocksTotal.toFloat()
        } else {
            0f
        }

    val progressPercent =
        (progress * 100).toInt()

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
            text = "Mi progreso",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Avanza a tu ritmo",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        // ------------------------------------------------
        // ESTA SEMANA
        // ------------------------------------------------

        Text(
            text = "ESTA SEMANA",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceBetween,
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                text =
                    "${uiState.progressBlocksDone} de ${uiState.progressBlocksTotal} bloques realizados",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "$progressPercent %",
                style = MaterialTheme.typography.labelLarge,
                color = SageGreen
            )
        }


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        // ------------------------------------------------
        // BARRA DE PROGRESO
        // ------------------------------------------------

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(
                    color =
                        MaterialTheme.colorScheme.outline.copy(
                            alpha = 0.35f
                        ),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(
                        fraction =
                            progress.coerceIn(
                                0f,
                                1f
                            )
                    )
                    .height(8.dp)
                    .background(
                        color = SageGreen,
                        shape =
                            RoundedCornerShape(20.dp)
                    )
            )
        }


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        HorizontalDivider(
            color =
                MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.45f
                )
        )


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        // ------------------------------------------------
        // MÉTRICAS
        // ------------------------------------------------

        Text(
            text =
                "${uiState.studiedTime} estudiadas",
            style =
                MaterialTheme.typography.bodyLarge,
            color =
                MaterialTheme.colorScheme.onSurface
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        Text(
            text =
                "${uiState.activeDays} días con actividad",
            style =
                MaterialTheme.typography.bodyLarge,
            color =
                MaterialTheme.colorScheme.onSurface
        )


        Spacer(
            modifier = Modifier.height(22.dp)
        )


        Text(
            text =
                uiState.consistencyChange,
            style =
                MaterialTheme.typography.labelLarge,
            color = SageGreen
        )


        Spacer(
            modifier = Modifier.height(30.dp)
        )


        // ------------------------------------------------
        // ACTIVIDAD SEMANAL
        // ------------------------------------------------

        Text(
            text = "ACTIVIDAD SEMANAL",
            style =
                MaterialTheme.typography.labelMedium,
            color =
                MaterialTheme.colorScheme.onBackground
        )


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        Row(
            modifier =
                Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            uiState.weekDays.forEach { day ->

                ProgressDayItem(
                    day = day
                )
            }
        }


        Spacer(
            modifier = Modifier.height(32.dp)
        )


        // ------------------------------------------------
        // MENSAJE DE PROGRESO
        // ------------------------------------------------

        Card(
            modifier =
                Modifier.fillMaxWidth(),
            shape =
                RoundedCornerShape(14.dp),
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

            Column(
                modifier =
                    Modifier.padding(16.dp)
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(
                        text = "Buen avance",
                        style =
                            MaterialTheme.typography.titleMedium,
                        color =
                            MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(
                        modifier = Modifier.width(6.dp)
                    )

                    Icon(
                        imageVector =
                            Icons.Default.Celebration,
                        contentDescription =
                            "Felicitación",
                        tint = SageGreen,
                        modifier =
                            Modifier.size(18.dp)
                    )
                }


                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )


                Text(
                    text =
                        uiState.progressMessage,
                    style =
                        MaterialTheme.typography.bodyMedium,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(30.dp)
        )


        // ------------------------------------------------
        // ÚLTIMAS 4 SEMANAS
        // ------------------------------------------------

        Text(
            text = "ÚLTIMAS 4 SEMANAS",
            style =
                MaterialTheme.typography.labelMedium,
            color =
                MaterialTheme.colorScheme.onBackground
        )


        Spacer(
            modifier =
                Modifier.height(18.dp)
        )


        Row(
            modifier =
                Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceEvenly,
            verticalAlignment =
                Alignment.Bottom
        ) {

            uiState.weekTrend.forEach { week ->

                WeekBarItem(
                    week = week
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(28.dp)
        )
    }
}


@Composable
private fun ProgressDayItem(
    day: ProgressDay
) {

    Column(
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text = day.label,
            style =
                MaterialTheme.typography.labelSmall,
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )


        Spacer(
            modifier =
                Modifier.height(6.dp)
        )


        Box(
            modifier =
                if (day.hasActivity) {

                    Modifier
                        .size(20.dp)
                        .background(
                            color = SageGreen,
                            shape = CircleShape
                        )

                } else {

                    Modifier
                        .size(20.dp)
                        .background(
                            color =
                                MaterialTheme.colorScheme.surface,
                            shape = CircleShape
                        )
                        .border(
                            width = 1.5.dp,
                            color =
                                MaterialTheme.colorScheme.outline,
                            shape = CircleShape
                        )
                }
        )
    }
}


@Composable
private fun WeekBarItem(
    week: WeekBar
) {

    val barHeight =
        (week.blocks * 8).dp

    Column(
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text =
                week.blocks.toString(),
            style =
                MaterialTheme.typography.labelSmall,
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )


        Spacer(
            modifier =
                Modifier.height(6.dp)
        )


        Box(
            modifier =
                Modifier
                    .width(28.dp)
                    .height(84.dp),
            contentAlignment =
                Alignment.BottomCenter
        ) {

            Box(
                modifier =
                    Modifier
                        .width(28.dp)
                        .height(barHeight)
                        .background(
                            color = SageGreen,
                            shape =
                                RoundedCornerShape(
                                    topStart = 6.dp,
                                    topEnd = 6.dp
                                )
                        )
            )
        }


        Spacer(
            modifier =
                Modifier.height(6.dp)
        )


        Text(
            text = week.label,
            style =
                MaterialTheme.typography.labelSmall,
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}