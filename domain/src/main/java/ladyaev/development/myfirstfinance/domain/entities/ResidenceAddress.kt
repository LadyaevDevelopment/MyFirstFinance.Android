package ladyaev.development.myfirstfinance.domain.entities

data class ResidenceAddress(
    val country: Country,
    val city: String,
    val street: String,
    val buildingNumber: String,
    val apartment: String
)