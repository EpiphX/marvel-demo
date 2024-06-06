package com.chris.marvel.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MarvelPrimary,
    secondary = MarvelDarkGrey,
    tertiary = MarvelDarkGrey,
    background = MarvelDarkGrey,
    surface = MarvelWhite,
    onPrimary = MarvelWhite,
    onSecondary = MarvelWhite,
    onTertiary = MarvelWhite,
    onBackground = MarvelWhite,
    onSurface = MarvelWhite
)

private val LightColorScheme = lightColorScheme(
    primary = MarvelWhite,
    secondary = MarvelWhite,
    tertiary = MarvelWhite,
    background = MarvelWhite,
    surface = MarvelDarkGrey,
    onPrimary = MarvelDarkGrey,
    onSecondary = MarvelDarkGrey,
    onTertiary = MarvelDarkGrey,
    onBackground = MarvelDarkGrey,
    onSurface = MarvelDarkGrey
)

@Composable
fun MarvelTheme(
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}