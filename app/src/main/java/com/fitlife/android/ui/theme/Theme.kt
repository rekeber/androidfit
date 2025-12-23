package com.fitlife.android.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = FitLifeBlue80,
    onPrimary = FitLifeBlue20,
    primaryContainer = FitLifeBlue30,
    onPrimaryContainer = FitLifeBlue90,
    secondary = FitLifeGreen80,
    onSecondary = FitLifeGreen20,
    secondaryContainer = FitLifeGreen30,
    onSecondaryContainer = FitLifeGreen90,
    tertiary = FitLifeOrange80,
    onTertiary = FitLifeOrange20,
    tertiaryContainer = FitLifeOrange30,
    onTertiaryContainer = FitLifeOrange90,
    error = FitLifeRed80,
    onError = FitLifeRed20,
    errorContainer = FitLifeRed30,
    onErrorContainer = FitLifeRed90,
    background = FitLifeNeutral10,
    onBackground = FitLifeNeutral90,
    surface = FitLifeNeutral10,
    onSurface = FitLifeNeutral90,
    surfaceVariant = FitLifeNeutralVariant30,
    onSurfaceVariant = FitLifeNeutralVariant80,
    outline = FitLifeNeutralVariant60
)

private val LightColorScheme = lightColorScheme(
    primary = FitLifeBlue40,
    onPrimary = FitLifeWhite,
    primaryContainer = FitLifeBlue90,
    onPrimaryContainer = FitLifeBlue10,
    secondary = FitLifeGreen40,
    onSecondary = FitLifeWhite,
    secondaryContainer = FitLifeGreen90,
    onSecondaryContainer = FitLifeGreen10,
    tertiary = FitLifeOrange40,
    onTertiary = FitLifeWhite,
    tertiaryContainer = FitLifeOrange90,
    onTertiaryContainer = FitLifeOrange10,
    error = FitLifeRed40,
    onError = FitLifeWhite,
    errorContainer = FitLifeRed90,
    onErrorContainer = FitLifeRed10,
    background = FitLifeNeutral99,
    onBackground = FitLifeNeutral10,
    surface = FitLifeNeutral99,
    onSurface = FitLifeNeutral10,
    surfaceVariant = FitLifeNeutralVariant90,
    onSurfaceVariant = FitLifeNeutralVariant30,
    outline = FitLifeNeutralVariant50
)

@Composable
fun FitLifeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}