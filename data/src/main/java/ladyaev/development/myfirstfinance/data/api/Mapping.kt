package ladyaev.development.myfirstfinance.data.api

import ladyaev.development.myFirstFinance.core.common.misc.Id
import ladyaev.development.myfirstfinance.core.api.enums.ApiUserStatus
import ladyaev.development.myfirstfinance.core.api.models.CountryModel
import ladyaev.development.myfirstfinance.core.api.models.PolicyDocumentModel
import ladyaev.development.myfirstfinance.domain.entities.Country
import ladyaev.development.myfirstfinance.domain.entities.PolicyDocument
import ladyaev.development.myfirstfinance.domain.entities.UserStatus

fun PolicyDocumentModel.toDomain(): PolicyDocument {
    return PolicyDocument(
        title = title,
        path = url
    )
}

fun CountryModel.toDomain(): Country {
    return Country(
        name = name,
        id = Id(id),
        phoneNumberCode = phoneNumberCode,
        flagPath = flagImageUrl,
        phoneNumberMasks = phoneNumberMasks
    )
}

fun ApiUserStatus.toDomain(): UserStatus {
    return when (this) {
        ApiUserStatus.NEED_TO_SPECIFY_BIRTH_DATE -> UserStatus.NeedToSpecifyBirthDate
        ApiUserStatus.NEED_TO_SPECIFY_NAME -> UserStatus.NeedToSpecifyName
        ApiUserStatus.NEED_TO_SPECIFY_EMAIL -> UserStatus.NeedToSpecifyEmail
        ApiUserStatus.NEED_TO_SPECIFY_RESIDENCE_ADDRESS -> UserStatus.NeedToSpecifyResidenceAddress
        ApiUserStatus.NEED_TO_CREATE_PIN_CODE -> UserStatus.NeedToCreatePinCode
        ApiUserStatus.NEED_TO_SPECIFY_IDENTITY_DOCUMENT -> UserStatus.NeedToSpecifyIdentityDocument
        ApiUserStatus.REGISTERED -> UserStatus.Registered
    }
}