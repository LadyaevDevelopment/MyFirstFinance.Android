package ladyaev.development.myFirstFinance.core.ui.controls.container

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ladyaev.development.myFirstFinance.core.ui.extensions.applyIf

@Composable
fun AnimatedVisibilityContainer(
    contentVisible: Boolean,
    content: @Composable (BoxScope.() -> Unit),
    animationListener: ((collapsed: Boolean) -> Unit)? = null
) {
    var fullHeight by remember { mutableStateOf(0.dp) }
    var heightMeasured by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .onGloballyPositioned { coordinates ->
                if (!heightMeasured && coordinates.size.height.dp.value > 0f) {
                    fullHeight = with(density) { coordinates.size.height.toDp() }
                    heightMeasured = true
                }
            }
            .animateContentSize(finishedListener = { initialValue, targetValue ->
                println(initialValue)
                println(targetValue)
                animationListener?.invoke(targetValue.height == 0)
            })
            .applyIf(heightMeasured) {
                height(if (contentVisible) fullHeight else 0.dp)
            }
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
        content = content
    )
}