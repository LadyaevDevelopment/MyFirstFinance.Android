package ladyaev.development.myFirstFinance.core.ui.theme

import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import ladyaev.development.myFirstFinance.core.ui.controls.button.ButtonTheme

val LocalButtonTheme = staticCompositionLocalOf {
    ButtonTheme(
        primary = ButtonColors(
            containerColor = Color.Unspecified,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
        ),
        secondary = ButtonColors(
            containerColor = Color.Unspecified,
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified
        )
    )
}