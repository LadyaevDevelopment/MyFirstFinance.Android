package ladyaev.development.myFirstFinance.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import ladyaev.development.myFirstFinance.core.ui.controls.button.ButtonTheme
import ladyaev.development.myFirstFinance.core.ui.theme.typography.AppTypography

object AppTheme {
    val buttonTheme: ButtonTheme
        @Composable
        get() = LocalButtonTheme.current
}

@Composable
fun AppTheme(
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
            window.statusBarColor = Color.White.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val buttonTheme = ButtonTheme(
        primary = ButtonColors(
            containerColor = AppColors.blue,
            contentColor = AppColors.white,
            disabledContainerColor = AppColors.lightGray,
            disabledContentColor = AppColors.black,
        ),
        secondary = ButtonColors(
            containerColor = AppColors.gray,
            contentColor = AppColors.black,
            disabledContainerColor = AppColors.lightGray,
            disabledContentColor = AppColors.black
        )
    )
    CompositionLocalProvider(LocalButtonTheme provides buttonTheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }
}