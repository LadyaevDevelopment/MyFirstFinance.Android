package ladyaev.development.myFirstFinance.domain.repository.setupUser

import ladyaev.development.myFirstFinance.core.common.Seconds

data class RequireConfirmationCodeData(
    val codeLength: Int,
    val codeId: String,
    val resendingTimeInterval: Seconds
)