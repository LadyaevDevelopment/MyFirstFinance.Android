package ladyaev.development.myFirstFinance.core.ui.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ladyaev.development.myFirstFinance.core.resources.R

class InDevelopmentDialog

@Composable
fun InDevelopmentDialog(
    visible: Boolean,
    onDismiss: () -> Unit
) {
    AlertDialog(
        title = stringResource(id = R.string.inDevelopment_title),
        message = stringResource(id = R.string.inDevelopment_message),
        iconDrawableId = R.drawable.ic_sign_clock,
        buttonText = stringResource(id = R.string.ok),
        visible = visible,
        onButtonClick = onDismiss,
        onDismiss = onDismiss
    )
}