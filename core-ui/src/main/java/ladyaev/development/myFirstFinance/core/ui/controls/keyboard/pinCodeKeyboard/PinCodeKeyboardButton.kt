package ladyaev.development.myFirstFinance.core.ui.controls.keyboard.pinCodeKeyboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
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

@Composable
fun PinCodeKeyboardButton(
    key: KeyboardButtonKey,
    isEnabled: Boolean,
    onClick: (key: KeyboardButtonKey) -> Unit
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(50.dp))
            .let {
                if (isEnabled) {
                    it.clickable(onClick = {
                        onClick(key)
                    })
                } else it
            }
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