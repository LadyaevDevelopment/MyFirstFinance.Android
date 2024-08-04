package ladyaev.development.myFirstFinance.core.ui.error

import androidx.annotation.DrawableRes
import ladyaev.development.myFirstFinance.core.resources.R

data class ErrorState(
    val visible: Boolean,
    val message: String,
    @DrawableRes val iconDrawableId: Int = R.drawable.ic_sign_warning
) {
    constructor(visible: Boolean) : this(visible, "")
}