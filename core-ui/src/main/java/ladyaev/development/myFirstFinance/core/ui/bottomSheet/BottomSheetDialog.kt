package ladyaev.development.myFirstFinance.core.ui.bottomSheet

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetLayout
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ModalBottomSheetValue
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetDialog(
    showBottomSheet: Boolean,
    onCompletelyClosed: () -> Unit,
    content: @Composable (BoxScope.() -> Unit)
) {
    @OptIn(ExperimentalMaterialApi::class)
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmValueChange = {
            if (it == ModalBottomSheetValue.Hidden) {
                onCompletelyClosed()
            }
            true
        }
    )

    @OptIn(ExperimentalMaterialApi::class)
    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContent = {
            BottomSheetLayout(content = content)
        },
        content = {}
    )

    @OptIn(ExperimentalMaterialApi::class)
    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet) {
            modalBottomSheetState.show()
        } else {
            modalBottomSheetState.hide()
        }
    }
}
