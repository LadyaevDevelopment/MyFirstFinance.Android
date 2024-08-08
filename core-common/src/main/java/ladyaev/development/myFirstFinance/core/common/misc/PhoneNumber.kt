package ladyaev.development.myFirstFinance.core.common.misc

data class PhoneNumber(
    val countryCode: String,
    val number: String
) {
    val length get() = toString().length

    val isEmpty get() = countryCode.isEmpty()

    override fun toString() = "$countryCode $number"

    constructor() : this("", "")

    constructor(countryCode: String) : this(countryCode, "")
}