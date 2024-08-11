package ladyaev.development.myfirstfinance.data.api.repositories

import ladyaev.development.myFirstFinance.core.common.utils.UserData
import ladyaev.development.myfirstfinance.core.api.apiclients.interfaces.MiscApiClient
import ladyaev.development.myfirstfinance.data.api.extensions.commonError
import ladyaev.development.myfirstfinance.data.api.toDomain
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.operation.StandardError
import ladyaev.development.myfirstfinance.domain.repositories.misc.MiscRepository
import ladyaev.development.myfirstfinance.domain.repositories.misc.requireCountries.RequireCountriesResult
import ladyaev.development.myfirstfinance.domain.repositories.misc.requirePolicyDocuments.RequirePolicyDocumentsResult
import javax.inject.Inject

class MiscRepositoryRemote @Inject constructor(
    private val miscApiClient: MiscApiClient,
    private val userData: UserData
) : MiscRepository {
    override suspend fun policyDocuments(): OperationResult<RequirePolicyDocumentsResult, Unit> {
        return try {
            val response = miscApiClient.policyDocuments(userData.accessToken)
            val result = response.responseData?.documents?.map { it.toDomain() }
            return if (result != null) {
                OperationResult.Success(RequirePolicyDocumentsResult(result))
            } else {
                OperationResult.StandardFailure(StandardError.Unknown(null))
            }
        } catch (ex: Exception) {
            OperationResult.StandardFailure(ex.commonError())
        }
    }

    override suspend fun countries(): OperationResult<RequireCountriesResult, Unit> {
        return try {
            val response = miscApiClient.countries(userData.accessToken)
            val result = response.responseData?.countries?.map { it.toDomain() }
            return if (result != null) {
                OperationResult.Success(RequireCountriesResult(result))
            } else {
                OperationResult.StandardFailure(StandardError.Unknown(null))
            }
        } catch (ex: Exception) {
            OperationResult.StandardFailure(ex.commonError())
        }
    }
}