package ladyaev.development.myFirstFinance.core.ui.controls.keyboard.digitalKeyboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.KeyboardButtonKey

@Composable
fun DigitalKeyBoard(
    onKeyTapped: (key: KeyboardButtonKey) -> Unit,
) {
    Box{
        Column {
            Row {
                DigitalKeyboardButton(
                    key = KeyboardButtonKey.Key1,
                    isEnabled = true,
                    onClick = onKeyTapped,
                    modifier = Modifier.weight(1f)
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                DigitalKeyboardButton(
                    key = KeyboardButtonKey.Key2,
                    isEnabled = true,
                    onClick = onKeyTapped,
                    modifier = Modifier.weight(1f)
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                DigitalKeyboardButton(
                    key = KeyboardButtonKey.Key3,
                    isEnabled = true,
                    onClick = onKeyTapped,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                DigitalKeyboardButton(
                    key = KeyboardButtonKey.Key4,
                    isEnabled = true,
                    onClick = onKeyTapped,
                    modifier = Modifier.weight(1f)
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                DigitalKeyboardButton(
                    key = KeyboardButtonKey.Key5,
                    isEnabled = true,
                    onClick = onKeyTapped,
                    modifier = Modifier.weight(1f)
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                DigitalKeyboardButton(
                    key = KeyboardButtonKey.Key6,
                    isEnabled = true,
                    onClick = onKeyTapped,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                DigitalKeyboardButton(
                    key = KeyboardButtonKey.Key7,
                    isEnabled = true,
                    onClick = onKeyTapped,
                    modifier = Modifier.weight(1f)
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                DigitalKeyboardButton(
                    key = KeyboardButtonKey.Key8,
                    isEnabled = true,
                    onClick = onKeyTapped,
                    modifier = Modifier.weight(1f)
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                DigitalKeyboardButton(
                    key = KeyboardButtonKey.Key9,
                    isEnabled = true,
                    onClick = onKeyTapped,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                DigitalKeyboardButton(
                    key = KeyboardButtonKey.Fake,
                    isEnabled = false,
                    onClick = onKeyTapped,
                    modifier = Modifier.weight(1f)
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                DigitalKeyboardButton(
                    key = KeyboardButtonKey.Key0,
                    isEnabled = true,
                    onClick = onKeyTapped,
                    modifier = Modifier.weight(1f)
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
                DigitalKeyboardButton(
                    key = KeyboardButtonKey.Delete,
                    isEnabled = true,
                    onClick = onKeyTapped,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}