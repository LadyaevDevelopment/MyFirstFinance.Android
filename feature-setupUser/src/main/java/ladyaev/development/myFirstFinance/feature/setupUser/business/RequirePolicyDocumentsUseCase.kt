package ladyaev.development.myFirstFinance.feature.setupUser.business

import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.misc.MiscRepository
import ladyaev.development.myfirstfinance.domain.repositories.misc.requirePolicyDocuments.RequirePolicyDocumentsResult

class RequirePolicyDocumentsUseCase(
    private val repository: MiscRepository
) {
    suspend fun process(): OperationResult<RequirePolicyDocumentsResult, Unit> {
        return repository.policyDocuments()
    }
}