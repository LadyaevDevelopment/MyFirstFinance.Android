package ladyaev.development.myFirstFinance.feature.setupUser.business

import ladyaev.development.myFirstFinance.core.common.misc.Name
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoResult

class SpecifyNameUseCase(
    private val repository: SetupUserRepository
) {
    suspend fun process(name: Name): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        if (name.lastName.isBlank()) {
            return OperationResult.SpecificFailure(SpecifyUserInfoError.InvalidData)
        }
        if (name.firstName.isBlank()) {
            return OperationResult.SpecificFailure(SpecifyUserInfoError.InvalidData)
        }
        if (name.middleName?.isBlank() == true) {
            return OperationResult.SpecificFailure(SpecifyUserInfoError.InvalidData)
        }
        return repository.specifyName(name)
    }
}