package ladyaev.development.myFirstFinance.core.ui.controls.progress.pinCodeProgress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PinCodeProgressView(
    markers: List<DotMarkerState>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Row {
            for ((index, dotState) in markers.withIndex()) {
                DotMarker(state = dotState)
                if (index < markers.lastIndex) {
                    Spacer(modifier = Modifier.width(32.dp))
                }
            }
        }
    }
}