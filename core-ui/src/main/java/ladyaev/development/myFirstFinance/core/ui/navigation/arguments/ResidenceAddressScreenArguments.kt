package ladyaev.development.myFirstFinance.core.ui.navigation.arguments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ladyaev.development.myFirstFinance.core.ui.navigation.models.CountryUiModel

@Parcelize
data class ResidenceAddressScreenArguments(
    val chosenCountry: CountryUiModel?
) : Parcelable {
    constructor() : this(null)
}