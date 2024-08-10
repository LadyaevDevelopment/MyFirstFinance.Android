package ladyaev.development.myfirstfinance.data.api

import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.misc.MiscRepository
import ladyaev.development.myfirstfinance.domain.repositories.misc.requireCountries.RequireCountriesResult
import ladyaev.development.myfirstfinance.domain.repositories.misc.requirePolicyDocuments.RequirePolicyDocumentsResult

class MiscRepositoryRemote : MiscRepository {
    override suspend fun policyDocuments(): OperationResult<RequirePolicyDocumentsResult, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun countries(): OperationResult<RequireCountriesResult, Unit> {
        TODO("Not yet implemented")
    }
}