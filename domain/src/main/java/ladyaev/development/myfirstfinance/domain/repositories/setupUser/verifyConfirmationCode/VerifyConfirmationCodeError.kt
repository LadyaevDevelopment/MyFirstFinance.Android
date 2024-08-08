package ladyaev.development.myfirstfinance.domain.repositories.setupUser.verifyConfirmationCode

import ladyaev.development.myFirstFinance.core.common.misc.Seconds

sealed class VerifyConfirmationCodeError {
    data object WrongCode : VerifyConfirmationCodeError()
    data class UserTemporaryBlocked(val blockingTime: Seconds) : VerifyConfirmationCodeError()
}