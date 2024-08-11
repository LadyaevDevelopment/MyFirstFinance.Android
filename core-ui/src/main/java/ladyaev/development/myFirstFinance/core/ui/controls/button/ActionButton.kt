package ladyaev.development.myFirstFinance.core.ui.controls.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ladyaev.development.myFirstFinance.core.ui.theme.AppColors

@Composable
fun ActionButton(
    text: String,
    onclick: () -> Unit,
    buttonColors: ButtonColors,
    enabled: Boolean = true,
    progressbarVisible: Boolean = false
) {
    Button(
        onClick = onclick,
        colors = buttonColors,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(0.dp),
        enabled = enabled
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.height(64.dp)
        ) {
            if (progressbarVisible) {
                CircularProgressIndicator(
                    color = if (enabled) AppColors.white else AppColors.black,
                    modifier = Modifier.size(32.dp)
                )
            } else {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                )
            }
        }
    }
}