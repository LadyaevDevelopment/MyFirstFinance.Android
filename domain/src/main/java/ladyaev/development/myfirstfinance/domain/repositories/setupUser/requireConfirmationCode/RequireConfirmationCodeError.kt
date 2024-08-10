package ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode

import ladyaev.development.myFirstFinance.core.common.misc.Seconds

sealed class RequireConfirmationCodeError {
    data object InvalidData : RequireConfirmationCodeError()
    data object WrongCode : RequireConfirmationCodeError()
    data object UserBlocked : RequireConfirmationCodeError()
    data class UserTemporaryBlocked(val blockingTime: Seconds) : RequireConfirmationCodeError()
}