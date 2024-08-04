package ladyaev.development.myFirstFinance.domain.repository.misc

import ladyaev.development.myFirstFinance.core.common.OperationResult

interface MiscRepository {
    suspend fun policyDocuments(): OperationResult<PolicyDocumentsData, Unit>
    suspend fun countries(): OperationResult<CountriesData, Unit>
}