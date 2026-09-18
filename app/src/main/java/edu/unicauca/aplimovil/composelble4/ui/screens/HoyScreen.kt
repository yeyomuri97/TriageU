package edu.unicauca.aplimovil.composelble4.ui.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityHighBg
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityHighText
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityLowBg
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityLowText
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityMedBg
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityMedText
import edu.unicauca.aplimovil.composelble4.ui.theme.SageGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.SoftGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.SurfaceWhite
import edu.unicauca.aplimovil.composelble4.ui.theme.TextPrimary
import edu.unicauca.aplimovil.composelble4.ui.theme.TextSecondary
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Hoy",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Text(
            text = "Prioriza tu tiempo. Avanza a tu ritmo.",
            fontSize = 14.sp,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Resumen rápido
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SummaryChip(
                label = "Recomendadas",
                value = "${uiState.todayActivities.count { it.recommended }}",
                modifier = Modifier.weight(1f)
            )
            SummaryChip(
                label = "Pendientes",
                value = "${uiState.todayActivities.count { !it.completed }}",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Actividades de hoy",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(12.dp))

        uiState.todayActivities.forEach { activity ->
            ActivityCard(
                activity = activity,
                onToggle = { onToggleComplete(activity.id) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun SummaryChip(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SoftGreen)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = SageGreen
        )
        Text(
            text = label,
            fontSize = 13.sp,
            color = TextSecondary
        )
    }
}

@Composable
private fun ActivityCard(
    activity: ActivityItem,
    onToggle: () -> Unit
) {
    val (bg, textColor, label) = when (activity.priority) {
        Priority.HIGH -> Triple(PriorityHighBg, PriorityHighText, "Alta")
        Priority.MEDIUM -> Triple(PriorityMedBg, PriorityMedText, "Media")
        Priority.LOW -> Triple(PriorityLowBg, PriorityLowText, "Baja")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceWhite)
            .border(1.dp, BorderGreen, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Check
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .clickable(onClick = onToggle)
                .then(
                    if (activity.completed)
                        Modifier.background(SageGreen)
                    else
                        Modifier.border(2.dp, BorderGreen, CircleShape)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (activity.completed) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Completada",
                    tint = SurfaceWhite,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = activity.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (activity.completed) TextSecondary else TextPrimary
            )
            Text(
                text = "${activity.subject} · ${activity.durationMin} min",
                fontSize = 13.sp,
                color = TextSecondary
            )
            if (activity.recommended) {
                Text(
                    text = "Recomendado",
                    fontSize = 12.sp,
                    color = SageGreen,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Prioridad chip
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .background(bg)
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        }
    }
}
