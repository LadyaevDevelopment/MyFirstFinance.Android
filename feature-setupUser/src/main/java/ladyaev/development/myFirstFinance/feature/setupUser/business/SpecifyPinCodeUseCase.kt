package ladyaev.development.myFirstFinance.feature.setupUser.business

import ladyaev.development.myFirstFinance.core.common.misc.Code
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoResult

class SpecifyPinCodeUseCase(
    private val repository: SetupUserRepository
) {
    suspend fun process(pinCode: Code): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        if (pinCode.data.isBlank()) {
            return OperationResult.SpecificFailure(SpecifyUserInfoError.InvalidData)
        }
        return repository.specifyPinCode(pinCode)
    }
}