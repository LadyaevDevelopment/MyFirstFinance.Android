package ladyaev.development.myFirstFinance.core.ui.controls.container

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AnimatedVisibilityContainer(
    contentVisible: Boolean,
    content: @Composable BoxScope.() -> Unit
) {
    AnimatedVisibility(
        visible = contentVisible,
        enter = expandVertically(),
        exit = shrinkVertically(),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}