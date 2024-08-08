package ladyaev.development.myfirstfinance.domain.repositories.misc

import ladyaev.development.myfirstfinance.domain.operation.OperationResult

interface MiscRepository {
    suspend fun policyDocuments(): OperationResult<PolicyDocumentsData, Unit>
    suspend fun countries(): OperationResult<CountriesData, Unit>
}