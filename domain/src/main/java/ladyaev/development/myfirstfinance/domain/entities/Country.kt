package ladyaev.development.myfirstfinance.domain.entities

import ladyaev.development.myFirstFinance.core.common.misc.Id

data class Country(
    val name: String,
    val id: Id,
    val phoneNumberCode: String,
    val flagPath: String,
    val phoneNumberMasks: List<String>
)