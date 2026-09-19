package edu.unicauca.aplimovil.composelble4.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = SageGreen,
    onPrimary = OnPrimary,
    primaryContainer = SoftGreen,
    onPrimaryContainer = SageGreenDark,
    secondary = SageGreenDark,
    onSecondary = OnPrimary,
    secondaryContainer = SoftGreen,
    onSecondaryContainer = SageGreenDark,
    tertiary = SageGreen,
    background = BackgroundGreen,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    surfaceVariant = SoftGreen,
    onSurfaceVariant = TextSecondary,
    outline = BorderGreen,
    outlineVariant = BorderGreen,
    error = PriorityHighText,
    onError = OnPrimary,
    errorContainer = PriorityHighBg,
    onErrorContainer = PriorityHighText
)

private val DarkColorScheme = darkColorScheme(
    primary = SoftGreen,
    onPrimary = SageGreenDark,
    primaryContainer = SageGreenDark,
    onPrimaryContainer = SoftGreen,
    secondary = SoftGreen,
    onSecondary = SageGreenDark,
    background = Color(0xFF121412),
    onBackground = Color(0xFFE8EDE9),
    surface = Color(0xFF1A1E1B),
    onSurface = Color(0xFFE8EDE9),
    surfaceVariant = Color(0xFF2A302C),
    onSurfaceVariant = Color(0xFFA8B5AE),
    outline = Color(0xFF4A554F)
)

@Composable
fun Composelble4Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
