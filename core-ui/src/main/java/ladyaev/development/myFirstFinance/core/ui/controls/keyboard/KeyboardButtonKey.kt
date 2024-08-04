package ladyaev.development.myFirstFinance.core.ui.controls.keyboard

import androidx.annotation.DrawableRes
import ladyaev.development.myFirstFinance.core.resources.R

sealed class KeyboardButtonKey(
    val string: String?,
    @DrawableRes val drawable: Int?
) {
    data object Key0 : KeyboardButtonKey("0", null)
    data object Key1 : KeyboardButtonKey("1", null)
    data object Key2 : KeyboardButtonKey("2", null)
    data object Key3 : KeyboardButtonKey("3", null)
    data object Key4 : KeyboardButtonKey("4", null)
    data object Key5 : KeyboardButtonKey("5", null)
    data object Key6 : KeyboardButtonKey("6", null)
    data object Key7 : KeyboardButtonKey("7", null)
    data object Key8 : KeyboardButtonKey("8", null)
    data object Key9 : KeyboardButtonKey("9", null)
    data object Delete : KeyboardButtonKey(null, R.drawable.ic_btn_clear)
    data object Fake : KeyboardButtonKey(null, null)
}