package ladyaev.development.myFirstFinance.core.ui.navigation.arguments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConfirmPinCodeScreenArguments(
    val pinCode: String
) : Parcelable {
    constructor() : this("")
}
