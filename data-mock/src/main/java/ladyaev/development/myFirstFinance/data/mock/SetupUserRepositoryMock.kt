package ladyaev.development.myFirstFinance.data.mock

import kotlinx.coroutines.delay
import ladyaev.development.myFirstFinance.core.common.OperationResult
import ladyaev.development.myFirstFinance.core.common.PhoneNumber
import ladyaev.development.myFirstFinance.core.common.Seconds
import ladyaev.development.myFirstFinance.domain.repository.setupUser.RequireConfirmationCodeData
import ladyaev.development.myFirstFinance.domain.repository.setupUser.RequireConfirmationCodeError
import ladyaev.development.myFirstFinance.domain.repository.setupUser.SetupUserRepository
import ladyaev.development.myFirstFinance.domain.repository.setupUser.VerifyConfirmationCodeError
import javax.inject.Inject

class SetupUserRepositoryMock @Inject constructor() : SetupUserRepository {
    override suspend fun requireConfirmationCode(phoneNumber: PhoneNumber): OperationResult<RequireConfirmationCodeData, RequireConfirmationCodeError> {
        delay(500)
        return OperationResult.Success(RequireConfirmationCodeData(5, "0", Seconds(10)))
    }

    override suspend fun verifyConfirmationCode(
        codeId: String,
        code: String
    ): OperationResult<Unit, VerifyConfirmationCodeError> {
        delay(500)
        return if (code == "11111") {
            OperationResult.SpecificFailure(VerifyConfirmationCodeError.WrongCode)
        } else {
            OperationResult.Success(Unit)
        }
    }
}