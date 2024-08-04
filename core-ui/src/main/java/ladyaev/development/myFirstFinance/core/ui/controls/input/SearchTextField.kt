package ladyaev.development.myFirstFinance.core.ui.controls.input

import androidx.compose.runtime.Composable
import ladyaev.development.myFirstFinance.core.resources.R

@Composable
fun SearchTextField(
    text: String,
    onSearchButtonClick: () -> Unit,
    onTextChanged: (value: String) -> Unit
) {
    CustomTextField(
        text = text,
        onTextChanged = onTextChanged,
        trailingButton = TextFieldButton(R.drawable.ic_search, onSearchButtonClick)
    )
}