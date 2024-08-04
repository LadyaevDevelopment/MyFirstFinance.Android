package ladyaev.development.myFirstFinance.core.ui.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ladyaev.development.myFirstFinance.core.resources.R
import ladyaev.development.myFirstFinance.core.ui.error.ErrorState

@Composable
fun DefaultErrorDialog(
    state: ErrorState,
    onDismiss: () -> Unit
) {
    AlertDialog(
        title = stringResource(id = R.string.error_title),
        message = state.message,
        iconDrawableId = state.iconDrawableId,
        buttonText = stringResource(id = R.string.ok),
        visible = state.visible,
        onButtonClick = onDismiss,
        onDismiss = onDismiss
    )
}