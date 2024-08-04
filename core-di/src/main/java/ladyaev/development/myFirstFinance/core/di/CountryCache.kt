package ladyaev.development.myFirstFinance.core.di

import ladyaev.development.myFirstFinance.core.common.Cache
import ladyaev.development.myFirstFinance.core.common.OperationResult
import ladyaev.development.myFirstFinance.domain.repository.misc.CountriesData
import ladyaev.development.myFirstFinance.domain.repository.misc.MiscRepository

class CountryCache(miscRepository: MiscRepository) : Cache<OperationResult<CountriesData, Unit>>(
    requestData = { miscRepository.countries() },
    dataValid = { it is OperationResult.Success }
)