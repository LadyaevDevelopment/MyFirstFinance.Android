package ladyaev.development.myFirstFinance.core.ui.controls.keyboard.pinCodeKeyboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import ladyaev.development.myFirstFinance.core.ui.controls.keyboard.KeyboardButtonKey

@Composable
fun PinCodeKeyboard(onKeyTapped: (key: KeyboardButtonKey) -> Unit) {
    Box {
        Column {
            Row {
                PinCodeKeyboardButton(key = KeyboardButtonKey.Key1, true, onKeyTapped)
                PinCodeKeyboardButton(key = KeyboardButtonKey.Key2, true, onKeyTapped)
                PinCodeKeyboardButton(key = KeyboardButtonKey.Key3, true, onKeyTapped)
            }
            Row {
                PinCodeKeyboardButton(key = KeyboardButtonKey.Key4, true, onKeyTapped)
                PinCodeKeyboardButton(key = KeyboardButtonKey.Key5, true, onKeyTapped)
                PinCodeKeyboardButton(key = KeyboardButtonKey.Key6, true, onKeyTapped)
            }
            Row {
                PinCodeKeyboardButton(key = KeyboardButtonKey.Key7, true, onKeyTapped)
                PinCodeKeyboardButton(key = KeyboardButtonKey.Key8, true, onKeyTapped)
                PinCodeKeyboardButton(key = KeyboardButtonKey.Key9, true, onKeyTapped)
            }
            Row {
                PinCodeKeyboardButton(key = KeyboardButtonKey.Fake, false, onKeyTapped)
                PinCodeKeyboardButton(key = KeyboardButtonKey.Key0, true, onKeyTapped)
                PinCodeKeyboardButton(key = KeyboardButtonKey.Delete, true, onKeyTapped)
            }
        }
    }
}