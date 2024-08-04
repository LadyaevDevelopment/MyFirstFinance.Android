package ladyaev.development.myFirstFinance.core.ui.controls.input.inputCell

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ladyaev.development.myFirstFinance.core.ui.theme.AppColors

@Composable
fun InputCell(
    text: String,
    state: InputCellState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(
                1.dp,
                when (state) {
                    InputCellState.Default -> AppColors.darkGray
                    InputCellState.Active -> AppColors.blue
                    InputCellState.Success -> AppColors.green
                    InputCellState.Error -> AppColors.red
                },
                RoundedCornerShape(24.dp)
            )
            .then(modifier)
    ) {
        BasicTextField(
            value = text,
            onValueChange = {},
            enabled = false,
            modifier = Modifier.align(Alignment.Center),
            textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 32.sp),
        )
    }
}