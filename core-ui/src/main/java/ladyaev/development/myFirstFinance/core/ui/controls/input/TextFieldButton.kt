package ladyaev.development.myFirstFinance.core.ui.controls.input

import androidx.annotation.DrawableRes

data class TextFieldButton(
    @DrawableRes val iconResourceId: Int,
    val onClick: () -> Unit
)