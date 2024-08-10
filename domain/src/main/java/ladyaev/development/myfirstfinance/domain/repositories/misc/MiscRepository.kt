package ladyaev.development.myfirstfinance.domain.repositories.misc

import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.misc.requireCountries.RequireCountriesResult
import ladyaev.development.myfirstfinance.domain.repositories.misc.requirePolicyDocuments.RequirePolicyDocumentsResult

interface MiscRepository {
    suspend fun policyDocuments(): OperationResult<RequirePolicyDocumentsResult, Unit>
    suspend fun countries(): OperationResult<RequireCountriesResult, Unit>
}