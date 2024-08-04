package ladyaev.development.myFirstFinance.core.ui.controls.input

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ladyaev.development.myFirstFinance.core.ui.controls.input.inputCell.InputCell
import ladyaev.development.myFirstFinance.core.ui.controls.input.inputCell.InputCellData

@Composable
fun CodeInput(cells: List<InputCellData>) {
    Row {
        cells.indices.forEach { index ->
            InputCell(
                text = cells[index].text,
                state = cells[index].state,
                modifier = Modifier.weight(1f)
            )
            if (index < cells.lastIndex) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}