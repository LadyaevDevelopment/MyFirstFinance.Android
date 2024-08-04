package ladyaev.development.myFirstFinance.core.ui.controls.keyboard.digitalKeyboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.KeyboardButtonKey
import ladyaev.development.myFirstFinance.core.ui.extensions.applyIf
import ladyaev.development.myFirstFinance.core.ui.theme.AppColors

@Composable
fun DigitalKeyboardButton(
    key: KeyboardButtonKey,
    isEnabled: Boolean,
    onClick: (key: KeyboardButtonKey) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .applyIf(isEnabled) {
                clickable(onClick = {
                    onClick(key)
                }).background(color = AppColors.lightGray)
            }
            .then(modifier)
    ) {
        if (key.string != null) {
            Text(
                text = key.string,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 32.sp
            )
        }
        if (key.drawable != null) {
            Image(
                painter = painterResource(id = key.drawable),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}