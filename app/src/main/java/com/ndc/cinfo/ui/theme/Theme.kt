package com.ndc.cinfo.ui.theme

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0XFFAFC6FF),
    onPrimary = Color(0XFF002D6D),
    primaryContainer = Color(0XFF00429A),
    onPrimaryContainer = Color(0XFFD9E2FF),
    secondary = Color(0XFFBFC6DC),
    onSecondary = Color(0XFF293042),
    secondaryContainer = Color(0XFF404659),
    onSecondaryContainer = Color(0XFFDBE2F9),
    tertiary = Color(0XFF9FCAFF),
    onTertiary = Color(0XFF003259),
    tertiaryContainer = Color(0XFF00497E),
    onTertiaryContainer = Color(0XFFD2E4FF),
    error = Color(0XFFFFB4AB),
    onError = Color(0XFF690005),
    errorContainer = Color(0XFF93000A),
    onErrorContainer = Color(0XFFFFDAD6),
    background = Color(0XFF1B1B1F),
    onBackground = Color(0XFFE3E2E6),
    surface = Color(0XFF1B1B1F),
    onSurface = Color(0XFFE3E2E6),
    surfaceVariant = Color(0XFF44464F),
    onSurfaceVariant = Color(0XFFC5C6D0),
    outline = Color(0XFF8F9099)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0XFF0759C7),
    onPrimary = Color(0XFFFFFFFF),
    primaryContainer = Color(0XFFD9E2FF),
    onPrimaryContainer = Color(0XFF001944),
    secondary = Color(0XFF575E71),
    onSecondary = Color(0XFFFFFFFF),
    secondaryContainer = Color(0XFFDBE2F9),
    onSecondaryContainer = Color(0XFF141B2C),
    tertiary = Color(0XFF0061A5),
    onTertiary = Color(0XFFFFFFFF),
    tertiaryContainer = Color(0XFFD2E4FF),
    onTertiaryContainer = Color(0XFF001D36),
    error = Color(0XFFBA1A1A),
    onError = Color(0XFFFFFFFF),
    errorContainer = Color(0XFFFFDAD6),
    onErrorContainer = Color(0XFF410002),
    background = Color(0XFFFEFBFF),
    onBackground = Color(0XFF1B1B1F),
    surface = Color(0XFFFEFBFF),
    onSurface = Color(0XFF1B1B1F),
    surfaceVariant = Color(0XFFE1E2EC),
    onSurfaceVariant = Color(0XFF44464F),
    outline = Color(0XFF757780)
)

@Composable
fun CinfoTheme(
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