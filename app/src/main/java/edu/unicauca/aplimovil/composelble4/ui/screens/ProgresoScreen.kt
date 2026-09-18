package edu.unicauca.aplimovil.composelble4.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.unicauca.aplimovil.composelble4.ui.theme.BorderGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.SageGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.SoftGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.SurfaceWhite
import edu.unicauca.aplimovil.composelble4.ui.theme.TextPrimary
import edu.unicauca.aplimovil.composelble4.ui.theme.TextSecondary
import edu.unicauca.aplimovil.composelble4.viewmodel.AppUiState

@Composable
fun ProgresoScreen(
    uiState: AppUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Tu progreso",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Text(
            text = "Avanza a tu ritmo. Lo importante es mantener el progreso.",
            fontSize = 14.sp,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Indicador principal
        Text(
            text = "${uiState.progressBlocksDone} de ${uiState.progressBlocksTotal} bloques realizados",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Barra de progreso
        val progress = uiState.progressBlocksDone.toFloat() / uiState.progressBlocksTotal
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(SoftGreen)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(5.dp))
                    .background(SageGreen)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Métricas
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricCard(
                value = uiState.studiedTime,
                label = "estudiadas",
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                value = "${uiState.activeDays} días",
                label = "con actividad",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Semana
        Text(
            text = "Esta semana",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            uiState.weekDays.forEach { day ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .then(
                                if (day.hasActivity)
                                    Modifier.background(SageGreen)
                                else
                                    Modifier
                                        .background(SurfaceWhite)
                                        .border(1.dp, BorderGreen, CircleShape)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (day.hasActivity) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = SurfaceWhite,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = day.label,
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Comparación
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SoftGreen)
                .padding(14.dp)
        ) {
            Text(
                text = uiState.consistencyChange,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = SageGreen
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Mensaje
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceWhite)
                .border(1.dp, BorderGreen, RoundedCornerShape(12.dp))
                .padding(14.dp)
        ) {
            Text(
                text = uiState.progressMessage,
                fontSize = 14.sp,
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Tendencia 4 semanas
        Text(
            text = "Últimas 4 semanas",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            uiState.weekTrend.forEach { bar ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${bar.blocks}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    val heightRatio = bar.blocks.toFloat() / bar.maxBlocks
                    Box(
                        modifier = Modifier
                            .width(36.dp)
                            .height((80 * heightRatio).dp.coerceAtLeast(8.dp))
                            .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                            .background(SageGreen)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = bar.label,
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceWhite)
            .border(1.dp, BorderGreen, RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Text(
            text = label,
            fontSize = 13.sp,
            color = TextSecondary
        )
    }
}
