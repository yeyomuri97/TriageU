package edu.unicauca.aplimovil.composelble4.ui.screens

import androidx.compose.foundation.border
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.unicauca.aplimovil.composelble4.ui.theme.BorderGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.SageGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.SurfaceWhite
import edu.unicauca.aplimovil.composelble4.ui.theme.TextPrimary
import edu.unicauca.aplimovil.composelble4.ui.theme.TextSecondary
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        // Marca
        Text(
            text = "TriageU",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = SageGreen
        )
        Text(
            text = "Prioriza tu tiempo. Avanza a tu ritmo.",
            fontSize = 13.sp,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Prepara tu semestre",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Cuéntanos qué estás cursando y cuándo sueles tener tiempo para estudiar.",
            fontSize = 14.sp,
            color = TextSecondary,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sección 1: Materias
        Text(
            text = "Mis materias",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(12.dp))

        uiState.subjects.forEach { subject ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, BorderGreen, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = subject.name,
                    fontSize = 15.sp,
                    color = TextPrimary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedButton(
            onClick = { /* Agregar materia — demo */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = SageGreen),
            border = androidx.compose.foundation.BorderStroke(1.dp, SageGreen)
        ) {
            Text("+ Agregar materia", fontWeight = FontWeight.Medium)
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Sección 2: Disponibilidad
        Text(
            text = "¿Cuándo sueles tener tiempo para estudiar?",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(12.dp))

        uiState.availability.forEach { avail ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, BorderGreen, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(avail.day, fontSize = 15.sp, color = TextPrimary)
                Text(avail.hours, fontSize = 14.sp, color = TextSecondary)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedButton(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = SageGreen),
            border = androidx.compose.foundation.BorderStroke(1.dp, SageGreen)
        ) {
            Text("Editar disponibilidad", fontWeight = FontWeight.Medium)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Acción principal
        Button(
            onClick = onComplete,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SageGreen)
        ) {
            Text(
                text = "Crear mi plan",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = SurfaceWhite
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = onSkip,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Configurar después",
                fontSize = 14.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}
