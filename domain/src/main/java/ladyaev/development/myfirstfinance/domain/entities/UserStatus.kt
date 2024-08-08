package ladyaev.development.myfirstfinance.domain.entities

enum class UserStatus {
    NeedToSpecifyBirthDate,
    NeedToSpecifyName,
    NeedToSpecifyEmail,
    NeedToSpecifyResidenceAddress,
    NeedToCreatePinCode,
    NeedToSpecifyIdentityDocument,
    Registered
}