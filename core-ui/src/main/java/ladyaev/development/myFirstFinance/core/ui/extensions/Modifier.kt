package ladyaev.development.myFirstFinance.core.ui.extensions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import ladyaev.development.myFirstFinance.core.ui.theme.AppColors

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = this.pointerInput(Unit) {
    detectTapGestures(onTap = { onClick() })
}

fun Modifier.applyIf(condition: Boolean, block: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        block(this)
    } else this
}