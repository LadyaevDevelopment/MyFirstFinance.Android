package ladyaev.development.myFirstFinance.domain.repository.setupUser

import ladyaev.development.myFirstFinance.core.common.Seconds

sealed class VerifyConfirmationCodeError {
    data object WrongCode : VerifyConfirmationCodeError()
    data class UserTemporaryBlocked(val blockingTime: Seconds) : VerifyConfirmationCodeError()
}