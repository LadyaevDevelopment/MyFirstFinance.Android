package ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode

import ladyaev.development.myFirstFinance.core.common.misc.Id
import ladyaev.development.myFirstFinance.core.common.misc.Length
import ladyaev.development.myFirstFinance.core.common.misc.Seconds

data class RequireConfirmationCodeResult(
    val codeLength: Length,
    val codeId: Id,
    val resendingTimeInterval: Seconds
)