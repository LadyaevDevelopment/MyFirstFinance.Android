package ladyaev.development.myFirstFinance.core.ui.navigation.arguments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ladyaev.development.myFirstFinance.core.ui.navigation.models.PhoneNumberUiModel

@Parcelize
data class ConfirmationCodeScreenArguments(
    val phoneNumber: PhoneNumberUiModel
) : Parcelable {
    constructor() : this(PhoneNumberUiModel("", ""))
}