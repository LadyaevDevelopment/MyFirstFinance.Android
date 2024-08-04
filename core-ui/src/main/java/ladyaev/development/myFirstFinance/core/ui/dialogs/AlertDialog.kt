package ladyaev.development.myFirstFinance.core.ui.dialogs

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ladyaev.development.myFirstFinance.core.ui.controls.button.ActionButton
import ladyaev.development.myFirstFinance.core.ui.theme.AppTheme

@Composable
fun AlertDialog(
    title: String,
    message: String,
    @DrawableRes iconDrawableId: Int? = null,
    buttonText: String,
    visible: Boolean,
    onButtonClick: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
) {
    if (visible) {
        Dialog(
            onDismissRequest = {
                onDismiss?.invoke()
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                tonalElevation = 24.dp,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (iconDrawableId != null) {
                        Image(
                            painter = painterResource(id = iconDrawableId),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Text(
                        title,
                        style = MaterialTheme.typography.titleSmall,
                        letterSpacing = -(1.sp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        message,
                        style = MaterialTheme.typography.bodyLarge,
                        letterSpacing = -(1.sp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ActionButton(
                        onclick = {
                            onButtonClick?.invoke()
                        },
                        text = buttonText,
                        buttonColors = AppTheme.buttonTheme.primary
                    )
                }
            }
        }
    }
}