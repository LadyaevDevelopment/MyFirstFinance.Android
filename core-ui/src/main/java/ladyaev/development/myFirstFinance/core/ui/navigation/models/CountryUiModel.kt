package ladyaev.development.myFirstFinance.core.ui.navigation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ladyaev.development.myFirstFinance.core.common.misc.Id
import ladyaev.development.myfirstfinance.domain.entities.Country

@Parcelize
data class CountryUiModel(
    val name: String,
    val id: String,
    val phoneNumberCode: String,
    val flagPath: String,
    val phoneNumberMasks: List<String>
) : Parcelable

fun CountryUiModel.toEntity(): Country {
    return Country(
        name = name,
        id = Id(id),
        phoneNumberCode = phoneNumberCode,
        flagPath = flagPath,
        phoneNumberMasks = phoneNumberMasks,
    )
}

fun Country.toUiModel(): CountryUiModel {
    return CountryUiModel(
        name = name,
        id = id.data,
        phoneNumberCode = phoneNumberCode,
        flagPath = flagPath,
        phoneNumberMasks = phoneNumberMasks,
    )
}