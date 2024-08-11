package ladyaev.development.myFirstFinance.core.ui.controls.input

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ladyaev.development.myFirstFinance.core.common.extensions.applyIf
import ladyaev.development.myFirstFinance.core.ui.theme.AppColors

@Composable
fun CustomTextField(
    text: String,
    onTextChanged: (value: String) -> Unit = {},
    placeholder: String = "",
    onFocusChanged: (hasFocus: Boolean) -> Unit = {},
    trailingButton: TextFieldButton? = null,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    focusRequester: FocusRequester? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var isFocused by remember { mutableStateOf(false) }

    @OptIn(ExperimentalMaterial3Api::class)
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(64.dp)
                .clip(RoundedCornerShape(24.dp))
                .applyIf(onClick != null) {
                    clickable(onClick = onClick!!)
                }
                .border(1.dp, AppColors.gray, RoundedCornerShape(24.dp))
                .padding(start = 24.dp, end = 8.dp)
        ) {
            BasicTextField(
                value = text,
                enabled = enabled,
                onValueChange = { onTextChanged(it) },
                keyboardOptions = keyboardOptions,
                modifier = Modifier
                    .weight(1f)
                    .applyIf(focusRequester != null) {
                        focusRequester(focusRequester!!)
                    }
                    .onFocusChanged {
                        isFocused = it.isFocused
                        onFocusChanged(it.isFocused)
                    },
                textStyle = MaterialTheme.typography.bodyLarge,
                decorationBox = { innerTextField ->
                    Box {
                        if (placeholder.isNotBlank() && text.isEmpty() && !isFocused) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.bodyLarge.copy(color = AppColors.darkGray),
                            )
                        }
                        innerTextField()
                    }
                }
            )
            if (trailingButton != null) {
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = trailingButton.onClick) {
                    Image(
                        painter = painterResource(id = trailingButton.iconResourceId),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}