package com.example.toiletkorea.ui.theme

import android.app.Activity
import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.toiletkorea.R

private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    background = md_theme_light_background,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
//    onSecondaryContainer = md_theme_light_onSecondaryContainer,
//    tertiary = md_theme_light_tertiary,
//    onTertiary = md_theme_light_onTertiary,
//    tertiaryContainer = md_theme_light_tertiaryContainer,
//    onTertiaryContainer = md_theme_light_onTertiaryContainer,
//    error = md_theme_light_error,
//    errorContainer = md_theme_light_errorContainer,
//    onError = md_theme_light_onError,
//    onErrorContainer = md_theme_light_onErrorContainer,
//    onBackground = md_theme_light_onBackground,
//    surface = md_theme_light_surface,
//    onSurface = md_theme_light_onSurface,
//    surfaceVariant = md_theme_light_surfaceVariant,
//    onSurfaceVariant = md_theme_light_onSurfaceVariant,
//    outline = md_theme_light_outline,
//    inverseOnSurface = md_theme_light_inverseOnSurface,
//    inverseSurface = md_theme_light_inverseSurface,
//    inversePrimary = md_theme_light_inversePrimary,
//    surfaceTint = md_theme_light_surfaceTint,
//    outlineVariant = md_theme_light_outlineVariant,
//    scrim = md_theme_light_scrim,
)


//private val DarkColors = darkColorScheme(
//    primary = md_theme_dark_primary,
//    onPrimary = md_theme_dark_onPrimary,
//    primaryContainer = md_theme_dark_primaryContainer,
//    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
//    secondary = md_theme_dark_secondary,
//    onSecondary = md_theme_dark_onSecondary,
//    secondaryContainer = md_theme_dark_secondaryContainer,
//    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
//    tertiary = md_theme_dark_tertiary,
//    onTertiary = md_theme_dark_onTertiary,
//    tertiaryContainer = md_theme_dark_tertiaryContainer,
//    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
//    error = md_theme_dark_error,
//    errorContainer = md_theme_dark_errorContainer,
//    onError = md_theme_dark_onError,
//    onErrorContainer = md_theme_dark_onErrorContainer,
//    background = md_theme_dark_background,
//    onBackground = md_theme_dark_onBackground,
//    surface = md_theme_dark_surface,
//    onSurface = md_theme_dark_onSurface,
//    surfaceVariant = md_theme_dark_surfaceVariant,
//    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
//    outline = md_theme_dark_outline,
//    inverseOnSurface = md_theme_dark_inverseOnSurface,
//    inverseSurface = md_theme_dark_inverseSurface,
//    inversePrimary = md_theme_dark_inversePrimary,
//    surfaceTint = md_theme_dark_surfaceTint,
//    outlineVariant = md_theme_dark_outlineVariant,
//    scrim = md_theme_dark_scrim,
//)

@Composable
fun ToiletKoreaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
//    val colorScheme = when {
////        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
////            val context = LocalContext.current
////            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
////        }
//
//        darkTheme -> DarkColors
//        else -> LightColors
//    }
    val colorScheme = LightColors
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    MaterialTheme(
        colorScheme = colorScheme ,
        typography = Typography,
        content = content,
        shapes = Shapes,
    )
}


@Composable
fun LoginTheme(@StringRes title: Int, @StringRes subtitle: Int, ) {
    Box(
        modifier = Modifier
            .size(width = 292.dp, height = 186.dp)
    )
    {
        Image(
            painterResource(id = R.drawable.main_logo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.TopCenter)
        )
        Column(modifier = Modifier.align(Alignment.BottomStart)) {
            Text(
                text = stringResource(id = title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(id = subtitle),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.displaySmall
            )
        }

    }
}
@Preview(showBackground = true)
@Composable
fun LoginThemePreview(){
    LoginTheme(title  = R.string.welcome_back, subtitle = R.string.login_to_continue)
}