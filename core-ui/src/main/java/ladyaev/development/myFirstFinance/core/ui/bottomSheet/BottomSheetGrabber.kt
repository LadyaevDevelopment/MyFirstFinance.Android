package ladyaev.development.myFirstFinance.core.ui.bottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ladyaev.development.myFirstFinance.core.ui.theme.AppColors

@Composable
fun BottomSheetGrabber() {
    Box(
        modifier = Modifier
            .width(32.dp)
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(AppColors.darkGray)
    )
}