package ladyaev.development.myFirstFinance.core.ui.navigation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ladyaev.development.myFirstFinance.core.common.misc.PhoneNumber

@Parcelize
data class PhoneNumberUiModel(
    val countryCode: String,
    val number: String
) : Parcelable

fun PhoneNumberUiModel.toEntity(): PhoneNumber {
    return PhoneNumber(
        countryCode = countryCode,
        number = number
    )
}

fun PhoneNumber.toUiModel(): PhoneNumberUiModel {
    return PhoneNumberUiModel(
        countryCode = countryCode,
        number = number
    )
}