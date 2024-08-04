package ladyaev.development.myFirstFinance.domain.repository.setupUser

import ladyaev.development.myFirstFinance.core.common.Seconds

sealed class RequireConfirmationCodeError {
    data object WrongCode : RequireConfirmationCodeError()
    data object UserBlocked : RequireConfirmationCodeError()
    data class UserTemporaryBlocked(val blockingTime: Seconds) : RequireConfirmationCodeError()
}