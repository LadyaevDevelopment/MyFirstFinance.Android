package ladyaev.development.myFirstFinance.core.ui.controls.progress.pinCodeProgress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ladyaev.development.myFirstFinance.core.ui.theme.AppColors

@Composable
fun DotMarker(state: DotMarkerState) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(when (state) {
                DotMarkerState.Default -> AppColors.gray
                DotMarkerState.Active -> AppColors.blue
                DotMarkerState.Success -> AppColors.green
                DotMarkerState.Error -> AppColors.red
            })
    )
}