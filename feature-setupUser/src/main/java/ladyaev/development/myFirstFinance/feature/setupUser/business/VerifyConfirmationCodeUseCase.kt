package ladyaev.development.myFirstFinance.feature.setupUser.business

import ladyaev.development.myFirstFinance.core.common.misc.Code
import ladyaev.development.myFirstFinance.core.common.misc.Id
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.verifyConfirmationCode.VerifyConfirmationCodeError

class VerifyConfirmationCodeUseCase(
    private val repository: SetupUserRepository
) {
    suspend fun process(codeId: Id, code: Code): OperationResult<Unit, VerifyConfirmationCodeError> {
        return repository.verifyConfirmationCode(codeId, code)
    }
}