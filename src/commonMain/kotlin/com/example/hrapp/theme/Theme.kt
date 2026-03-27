package com.example.hrapp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = LightPrimaryBrand,
    onPrimary = LightBackgroundPrimary,
    primaryContainer = LightPrimaryLight,
    onPrimaryContainer = LightPrimaryDark,
    secondary = LightSecondary,
    onSecondary = LightBackgroundPrimary,
    error = LightError,
    onError = LightBackgroundPrimary,
    background = LightBackgroundPrimary,
    onBackground = LightTextPrimary,
    surface = LightBackgroundSecondary,
    onSurface = LightTextPrimary,
    surfaceVariant = LightBackgroundTertiary,
    onSurfaceVariant = LightTextSecondary,
    outline = LightBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimaryBrand,
    onPrimary = DarkBackgroundPrimary,
    primaryContainer = DarkPrimaryLight,
    onPrimaryContainer = DarkPrimaryDark,
    secondary = DarkSecondary,
    onSecondary = DarkBackgroundPrimary,
    error = DarkError,
    onError = DarkBackgroundPrimary,
    background = DarkBackgroundPrimary,
    onBackground = DarkTextPrimary,
    surface = DarkBackgroundSecondary,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkBackgroundTertiary,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkBorder
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // If ThemeState is not null, use it. Otherwise, fallback to the system default.
    val useDarkTheme = ThemeState.isDarkModeEnabled ?: darkTheme

    val colorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
