package ladyaev.development.myfirstfinance.domain.repositories.setupUser

import ladyaev.development.myfirstfinance.domain.entities.UserStatus

data class SpecifyUserInfoResult(
    val userStatus: UserStatus,
    val pinCodeLength: Int?
)
