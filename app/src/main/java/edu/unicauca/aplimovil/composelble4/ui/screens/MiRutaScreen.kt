package edu.unicauca.aplimovil.composelble4.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.unicauca.aplimovil.composelble4.ui.theme.BorderGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityMedBg
import edu.unicauca.aplimovil.composelble4.ui.theme.PriorityMedText
import edu.unicauca.aplimovil.composelble4.ui.theme.SageGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.SoftGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.SurfaceWhite
import edu.unicauca.aplimovil.composelble4.ui.theme.TextPrimary
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Mi Ruta",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Text(
            text = "Tus próximos compromisos y puntos de atención.",
            fontSize = 14.sp,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Horizonte
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .background(SoftGreen)
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Próximos 10 días",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = SageGreen
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Timeline
        uiState.timeline.forEachIndexed { index, item ->
            TimelineRow(
                item = item,
                isLast = index == uiState.timeline.lastIndex
            )
        }
    }
}

@Composable
private fun TimelineRow(
    item: TimelineItem,
    isLast: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Línea + punto
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(28.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(if (item.isToday) 14.dp else 11.dp)
                    .clip(CircleShape)
                    .then(
                        if (item.isToday || item.isAlert)
                            Modifier.background(if (item.isAlert) PriorityMedText else SageGreen)
                        else
                            Modifier.border(2.dp, BorderGreen, CircleShape)
                    )
            )
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(if (item.isAlert) 56.dp else 48.dp)
                        .background(BorderGreen)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (isLast) 0.dp else 8.dp)
        ) {
            if (item.isAlert) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(PriorityMedBg)
                        .padding(12.dp)
                ) {
                    Column {
                        Text(
                            text = item.dateLabel,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = PriorityMedText
                        )
                        Text(
                            text = item.title,
                            fontSize = 14.sp,
                            color = PriorityMedText
                        )
                    }
                }
            } else {
                Text(
                    text = item.dateLabel,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (item.isToday) SageGreen else TextSecondary
                )
                Text(
                    text = item.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                if (item.meta.isNotBlank()) {
                    Text(
                        text = item.meta,
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
