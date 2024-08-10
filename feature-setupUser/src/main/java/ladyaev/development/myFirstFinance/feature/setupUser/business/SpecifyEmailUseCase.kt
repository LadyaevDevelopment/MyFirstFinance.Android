package ladyaev.development.myFirstFinance.feature.setupUser.business

import ladyaev.development.myFirstFinance.core.common.misc.Email
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoResult

class SpecifyEmailUseCase(
    private val repository: SetupUserRepository
) {
    suspend fun process(email: Email): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        if (email.data.isBlank()) {
            return OperationResult.SpecificFailure(SpecifyUserInfoError.InvalidData)
        }
        return repository.specifyEmail(email)
    }
}