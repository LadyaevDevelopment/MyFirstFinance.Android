package ladyaev.development.myfirstfinance.domain.repositories.setupUser.common

import ladyaev.development.myFirstFinance.core.common.misc.Length
import ladyaev.development.myfirstfinance.domain.entities.UserStatus

data class SpecifyUserInfoResult(
    val userStatus: UserStatus,
    val pinCodeLength: Length?
)
