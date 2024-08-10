package ladyaev.development.myFirstFinance.core.di

import ladyaev.development.myFirstFinance.core.common.utils.Cache
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.misc.requireCountries.RequireCountriesResult
import ladyaev.development.myfirstfinance.domain.repositories.misc.MiscRepository

class CountryCache(miscRepository: MiscRepository) : Cache<OperationResult<RequireCountriesResult, Unit>>(
    requestData = { miscRepository.countries() },
    dataValid = { it is OperationResult.Success }
)