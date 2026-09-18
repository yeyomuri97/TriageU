package edu.unicauca.aplimovil.composelble4.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
fun PerfilScreen(
    uiState: AppUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .background(SoftGreen),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = SageGreen,
                modifier = Modifier.size(44.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Estudiante TriageU",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Text(
            text = "Prioriza tu tiempo. Avanza a tu ritmo.",
            fontSize = 13.sp,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(28.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceWhite)
                .border(1.dp, BorderGreen, RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "Mis materias",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                uiState.subjects.forEach { s ->
                    Text(
                        text = "• ${s.name}",
                        fontSize = 15.sp,
                        color = TextPrimary,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
}
