package edu.unicauca.aplimovil.composelble4.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.unicauca.aplimovil.composelble4.ui.theme.BorderGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityMedBg
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityMedText
import edu.unicauca.aplimovil.composelble4.ui.theme.SageGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.TextSecondary
import edu.unicauca.aplimovil.composelble4.viewmodel.AppUiState
import edu.unicauca.aplimovil.composelble4.viewmodel.TimelineItem

@Composable
fun MiRutaScreen(
    uiState: AppUiState,
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
            text = "Mi ruta",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        Text(
            text = "Próximos 10 días",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        // ------------------------------------------------
        // LÍNEA DE TIEMPO
        // ------------------------------------------------

        uiState.timeline.forEachIndexed { index, item ->

            if (item.isAlert) {

                AlertTimelineItem()

            } else {

                TimelineEvent(
                    item = item,
                    isLast =
                        index ==
                                uiState.timeline.lastIndex
                )
            }
        }


        Spacer(
            modifier = Modifier.height(24.dp)
        )
    }
}


@Composable
private fun TimelineEvent(
    item: TimelineItem,
    isLast: Boolean
) {

    val nodeColor =
        when {

            item.isToday ->
                SageGreen

            item.dateLabel ==
                    "PRÓXIMA SEMANA" ->
                BorderGreen

            else ->
                TextSecondary
        }


    Row {

        // ------------------------------------------------
        // COLUMNA DEL NODO Y LA LÍNEA
        // ------------------------------------------------

        Column(
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(
                        color = nodeColor,
                        shape = CircleShape
                    )
            )


            if (!isLast) {

                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(
                            if (item.isToday) {
                                82.dp
                            } else {
                                92.dp
                            }
                        )
                        .background(
                            MaterialTheme
                                .colorScheme
                                .outline
                        )
                )
            }
        }


        Spacer(
            modifier = Modifier.width(16.dp)
        )


        // ------------------------------------------------
        // CONTENIDO
        // ------------------------------------------------

        Column(
            modifier =
                Modifier.padding(
                    bottom = 20.dp
                )
        ) {

            Text(
                text = item.dateLabel,
                style =
                    MaterialTheme.typography.labelSmall,
                color =
                    if (item.isToday) {
                        SageGreen
                    } else {
                        TextSecondary
                    }
            )


            Spacer(
                modifier = Modifier.height(6.dp)
            )


            // Etiqueta PARCIAL
            if (
                item.badge.isNotBlank()
            ) {

                Box(
                    modifier = Modifier
                        .background(
                            color =
                                MaterialTheme
                                    .colorScheme
                                    .errorContainer,

                            shape =
                                RoundedCornerShape(6.dp)
                        )
                        .padding(
                            horizontal = 7.dp,
                            vertical = 3.dp
                        )
                ) {

                    Text(
                        text = item.badge,
                        style =
                            MaterialTheme
                                .typography
                                .labelSmall,
                        color =
                            MaterialTheme
                                .colorScheme
                                .error
                    )
                }


                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )
            }


            Text(
                text = item.title,
                style =
                    MaterialTheme.typography.titleMedium,
                color =
                    if (
                        item.dateLabel ==
                        "PRÓXIMA SEMANA"
                    ) {

                        TextSecondary

                    } else {

                        MaterialTheme
                            .colorScheme
                            .onSurface
                    }
            )


            if (
                item.meta.isNotBlank()
            ) {

                Spacer(
                    modifier =
                        Modifier.height(2.dp)
                )

                Text(
                    text = item.meta,
                    style =
                        MaterialTheme.typography.bodySmall,
                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )
            }


            if (
                item.detail.isNotBlank()
            ) {

                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )


                if (item.isToday) {

                    Box(
                        modifier = Modifier
                            .background(
                                color =
                                    PriorityMedBg,
                                shape =
                                    RoundedCornerShape(6.dp)
                            )
                            .padding(
                                horizontal = 7.dp,
                                vertical = 3.dp
                            )
                    ) {

                        Text(
                            text = item.detail,
                            style =
                                MaterialTheme
                                    .typography
                                    .labelSmall,
                            color =
                                PriorityMedText
                        )
                    }

                } else {

                    Text(
                        text = item.detail,
                        style =
                            MaterialTheme
                                .typography
                                .bodySmall,
                        color =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                }
            }
        }
    }
}


@Composable
private fun AlertTimelineItem() {

    Row {

        // Dejamos el mismo espacio que ocupa
        // la columna de nodos.
        Spacer(
            modifier =
                Modifier.width(32.dp)
        )


        Box(
            modifier = Modifier
                .background(
                    color = PriorityMedBg,
                    shape =
                        RoundedCornerShape(8.dp)
                )
                .padding(
                    horizontal = 8.dp,
                    vertical = 5.dp
                )
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    imageVector =
                        Icons.Default.WarningAmber,
                    contentDescription =
                        "Carga alta",
                    tint =
                        PriorityMedText,
                    modifier =
                        Modifier.size(14.dp)
                )


                Spacer(
                    modifier =
                        Modifier.width(4.dp)
                )


                Text(
                    text = "Carga alta",
                    style =
                        MaterialTheme
                            .typography
                            .labelSmall,
                    color =
                        PriorityMedText
                )
            }
        }
    }


    Spacer(
        modifier =
            Modifier.height(16.dp)
    )
}