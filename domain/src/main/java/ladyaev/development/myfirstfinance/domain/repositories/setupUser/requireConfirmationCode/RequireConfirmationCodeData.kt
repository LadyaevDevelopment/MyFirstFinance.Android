package ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode

import ladyaev.development.myFirstFinance.core.common.misc.Seconds

data class RequireConfirmationCodeData(
    val codeLength: Int,
    val codeId: String,
    val resendingTimeInterval: Seconds
)