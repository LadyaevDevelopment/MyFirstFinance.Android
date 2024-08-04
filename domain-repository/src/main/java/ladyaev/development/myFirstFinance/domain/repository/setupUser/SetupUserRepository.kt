package ladyaev.development.myFirstFinance.domain.repository.setupUser

import ladyaev.development.myFirstFinance.core.common.OperationResult
import ladyaev.development.myFirstFinance.core.common.PhoneNumber

interface SetupUserRepository {
    suspend fun requireConfirmationCode(
        phoneNumber: PhoneNumber
    ): OperationResult<RequireConfirmationCodeData, RequireConfirmationCodeError>

    suspend fun verifyConfirmationCode(
        codeId: String,
        code: String
    ): OperationResult<Unit, VerifyConfirmationCodeError>
}