package edu.unicauca.aplimovil.composelble4.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.unicauca.aplimovil.composelble4.ui.theme.BorderGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.SageGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.SoftGreen
import edu.unicauca.aplimovil.composelble4.ui.theme.SurfaceWhite
import edu.unicauca.aplimovil.composelble4.ui.theme.TextPrimary
import edu.unicauca.aplimovil.composelble4.ui.theme.TextSecondary

@Composable
fun AcercaScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBack
            ) {

                Icon(
                    imageVector =
                        Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = TextPrimary
                )
            }

            Text(
                text = "Acerca de TriageU",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "TriageU",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = SageGreen
        )

        Text(
            text = "Prioriza tu tiempo. Avanza a tu ritmo.",
            fontSize = 14.sp,
            color = TextSecondary
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    SoftGreen,
                    RoundedCornerShape(16.dp)
                )
                .padding(18.dp)
        ) {

            Text(
                text = "¿Qué es TriageU?",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text =
                    "TriageU es una aplicación de apoyo para estudiantes " +
                            "universitarios que permite organizar y priorizar sus " +
                            "actividades académicas de acuerdo con factores como " +
                            "la fecha de entrega, el porcentaje de la nota, el nivel " +
                            "de preparación, el tiempo estimado y la disponibilidad " +
                            "del estudiante.",
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = TextSecondary
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    SurfaceWhite,
                    RoundedCornerShape(16.dp)
                )
                .border(
                    1.dp,
                    BorderGreen,
                    RoundedCornerShape(16.dp)
                )
                .padding(18.dp)
        ) {

            Text(
                text = "Funcionamiento",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text =
                    "La aplicación permite registrar materias y actividades, " +
                            "generar una priorización académica, consultar una ruta " +
                            "de trabajo y realizar seguimiento al progreso. Los datos " +
                            "pueden almacenarse localmente mediante Room y sincronizarse " +
                            "con Firebase para disponer de persistencia local y remota.",
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = TextSecondary
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    SurfaceWhite,
                    RoundedCornerShape(16.dp)
                )
                .border(
                    1.dp,
                    BorderGreen,
                    RoundedCornerShape(16.dp)
                )
                .padding(18.dp)
        ) {

            Text(
                text = "Tecnologías utilizadas",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "• Kotlin",
                fontSize = 14.sp,
                color = TextPrimary
            )

            Text(
                text = "• Jetpack Compose",
                fontSize = 14.sp,
                color = TextPrimary
            )

            Text(
                text = "• Material 3",
                fontSize = 14.sp,
                color = TextPrimary
            )

            Text(
                text = "• ViewModel y StateFlow",
                fontSize = 14.sp,
                color = TextPrimary
            )

            Text(
                text = "• Navigation Compose",
                fontSize = 14.sp,
                color = TextPrimary
            )

            Text(
                text = "• Room",
                fontSize = 14.sp,
                color = TextPrimary
            )

            Text(
                text = "• Firebase",
                fontSize = 14.sp,
                color = TextPrimary
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    SurfaceWhite,
                    RoundedCornerShape(16.dp)
                )
                .border(
                    1.dp,
                    BorderGreen,
                    RoundedCornerShape(16.dp)
                )
                .padding(18.dp)
        ) {

            Text(
                text = "Créditos",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Desarrollado por:",
                fontSize = 13.sp,
                color = TextSecondary
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Aurelio José Muñoz Rios",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "Isabella Plaza Díaz",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "Isabella Perez Hoyos",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "TriageU · Desarrollo de Aplicaciones Móviles",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 12.sp,
            color = TextSecondary
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )
    }
}