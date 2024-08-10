package ladyaev.development.myFirstFinance.feature.setupUser.business

import kotlinx.coroutines.CoroutineScope
import ladyaev.development.myFirstFinance.core.di.CountryCache
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.misc.requireCountries.RequireCountriesResult

class RequireCountriesUseCase(
    private val countryCache: CountryCache
) {
    suspend fun process(scope: CoroutineScope): OperationResult<RequireCountriesResult, Unit> {
        return countryCache.data(scope)
    }
}