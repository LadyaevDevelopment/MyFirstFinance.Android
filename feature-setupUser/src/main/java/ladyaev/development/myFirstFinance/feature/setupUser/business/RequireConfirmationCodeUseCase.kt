package ladyaev.development.myFirstFinance.feature.setupUser.business

import ladyaev.development.myFirstFinance.core.common.misc.PhoneNumber
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeResult

class RequireConfirmationCodeUseCase(
    private val repository: SetupUserRepository
) {
    suspend fun process(phoneNumber: PhoneNumber): OperationResult<RequireConfirmationCodeResult, RequireConfirmationCodeError> {
        if (phoneNumber.isEmpty) {
            return OperationResult.SpecificFailure(RequireConfirmationCodeError.InvalidData)
        }
        return repository.requireConfirmationCode(phoneNumber)
    }
}