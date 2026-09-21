package edu.unicauca.aplimovil.composelble4.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val TriageUColorScheme = lightColorScheme(

    // Color principal
    primary = SageGreen,
    onPrimary = OnPrimary,

    // Contenedor principal
    primaryContainer = SoftGreen,
    onPrimaryContainer = SageGreenDark,

    // Color secundario
    secondary = SageGreenDark,
    onSecondary = OnPrimary,

    // Contenedor secundario
    secondaryContainer = SoftGreen,
    onSecondaryContainer = SageGreenDark,

    // Fondo general
    background = BackgroundGreen,
    onBackground = TextPrimary,

    // Superficies
    surface = SurfaceWhite,
    onSurface = TextPrimary,

    // Variantes de superficie
    surfaceVariant = SoftGreen,
    onSurfaceVariant = TextSecondary,

    // Bordes
    outline = BorderGreen,

    // Estados de error
    error = PriorityHighText,
    errorContainer = PriorityHighBg,
    onError = OnPrimary,
    onErrorContainer = PriorityHighText
)

private val TriageUShapes = Shapes(

    small = RoundedCornerShape(12.dp),

    medium = RoundedCornerShape(16.dp),

    large = RoundedCornerShape(20.dp)
)

@Composable
fun Composelble4Theme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = TriageUColorScheme,
        typography = Typography,
        shapes = TriageUShapes,
        content = content
    )
}