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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import edu.unicauca.aplimovil.composelble4.viewmodel.Priority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaActividadScreen(
    uiState: AppUiState,
    onTitleChange: (String) -> Unit,
    onSubjectChange: (String) -> Unit,
    onDurationChange: (String) -> Unit,
    onPriorityChange: (Priority) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Nueva actividad",
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SurfaceWhite
                )
            )
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text("Título", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = uiState.newActivityTitle,
                onValueChange = onTitleChange,
                placeholder = { Text("Ej. Preparar parcial", color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SageGreen,
                    unfocusedBorderColor = BorderGreen,
                    focusedContainerColor = SurfaceWhite,
                    unfocusedContainerColor = SurfaceWhite
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Materia", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = uiState.newActivitySubject,
                onValueChange = onSubjectChange,
                placeholder = { Text("Selecciona o escribe", color = TextSecondary) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SageGreen,
                    unfocusedBorderColor = BorderGreen,
                    focusedContainerColor = SurfaceWhite,
                    unfocusedContainerColor = SurfaceWhite
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Duración (minutos)", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = uiState.newActivityDuration,
                onValueChange = onDurationChange,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SageGreen,
                    unfocusedBorderColor = BorderGreen,
                    focusedContainerColor = SurfaceWhite,
                    unfocusedContainerColor = SurfaceWhite
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Prioridad", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    "Alta" to Priority.HIGH,
                    "Media" to Priority.MEDIUM,
                    "Baja" to Priority.LOW
                ).forEach { (label, prio) ->
                    val selected = uiState.newActivityPriority == prio
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (selected) SoftGreen else SurfaceWhite)
                            .border(1.dp, if (selected) SageGreen else BorderGreen, RoundedCornerShape(18.dp))
                            .clickable { onPriorityChange(prio) }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = label,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (selected) SageGreen else TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    onSave()
                    onBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SageGreen)
            ) {
                Text(
                    text = "Guardar actividad",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = SurfaceWhite
                )
            }
        }
    }
}
