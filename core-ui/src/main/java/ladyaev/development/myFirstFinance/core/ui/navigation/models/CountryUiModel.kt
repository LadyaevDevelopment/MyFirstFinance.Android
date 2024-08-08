package ladyaev.development.myFirstFinance.core.ui.navigation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ladyaev.development.myfirstfinance.domain.entities.Country

@Parcelize
data class CountryUiModel(
    val name: String,
    val phoneNumberCode: String,
    val flagPath: String,
    val phoneNumberMasks: List<String>
) : Parcelable

fun CountryUiModel.toEntity(): ladyaev.development.myfirstfinance.domain.entities.Country {
    return ladyaev.development.myfirstfinance.domain.entities.Country(
        name = name,
        phoneNumberCode = phoneNumberCode,
        flagPath = flagPath,
        phoneNumberMasks = phoneNumberMasks,
    )
}

fun ladyaev.development.myfirstfinance.domain.entities.Country.toUiModel(): CountryUiModel {
    return CountryUiModel(
        name = name,
        phoneNumberCode = phoneNumberCode,
        flagPath = flagPath,
        phoneNumberMasks = phoneNumberMasks,
    )
}