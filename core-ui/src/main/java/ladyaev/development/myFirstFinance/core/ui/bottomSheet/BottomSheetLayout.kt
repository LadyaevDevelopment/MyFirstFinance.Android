package ladyaev.development.myFirstFinance.core.ui.bottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import ladyaev.development.myFirstFinance.core.ui.theme.AppColors

@Composable
fun BottomSheetLayout(content: @Composable (BoxScope.() -> Unit)) {
    Box(modifier = Modifier
        .clipToBounds()
        .clip(RoundedCornerShape(24.dp))
        .background(AppColors.white)
        .padding(horizontal = 24.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(12.dp))
            BottomSheetGrabber()
            Spacer(modifier = Modifier.height(12.dp))
            Box(content = content)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
