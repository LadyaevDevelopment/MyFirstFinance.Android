package ladyaev.development.myFirstFinance.core.di

import ladyaev.development.myFirstFinance.core.common.utils.Cache
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.misc.CountriesData
import ladyaev.development.myfirstfinance.domain.repositories.misc.MiscRepository

class CountryCache(miscRepository: MiscRepository) : Cache<OperationResult<CountriesData, Unit>>(
    requestData = { miscRepository.countries() },
    dataValid = { it is OperationResult.Success }
)