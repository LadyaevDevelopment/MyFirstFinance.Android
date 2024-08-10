package ladyaev.development.myfirstfinance.domain.entities

data class Country(
    val name: String,
    val iso2Code: String,
    val phoneNumberCode: String,
    val flagPath: String,
    val phoneNumberMasks: List<String>
)