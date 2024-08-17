package ladyaev.development.myFirstFinance.core.ui.controls.scaffold

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ladyaev.development.myFirstFinance.core.common.extensions.applyIf
import ladyaev.development.myFirstFinance.core.ui.extensions.noRippleClickable

@Composable
fun CustomScaffold(
    toolbar: @Composable (ColumnScope.() -> Unit)?,
    content: @Composable (ColumnScope.() -> Unit),
    contentScrollable: Boolean = true,
    useContentHorizontalPadding: Boolean = true,
    onClick: (() -> Unit)? = null,
    contentScrollState: ScrollState? = null,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(Modifier
        .background(color = Color.White)
        .fillMaxHeight()
        .applyIf(onClick != null) {
            noRippleClickable(onClick = onClick!!)
        }
    ) {
        val scrollState = contentScrollState ?: rememberScrollState()
        if (toolbar != null) {
            toolbar()
        }
        Column(
            horizontalAlignment = horizontalAlignment,
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    bottom = 16.dp,
                    start = if (useContentHorizontalPadding) 24.dp else 0.dp,
                    end = if (useContentHorizontalPadding) 24.dp else 0.dp
                )
                .fillMaxSize()
                .applyIf(contentScrollable) {
                    verticalScroll(scrollState)
                },
            content = content
        )
    }
}