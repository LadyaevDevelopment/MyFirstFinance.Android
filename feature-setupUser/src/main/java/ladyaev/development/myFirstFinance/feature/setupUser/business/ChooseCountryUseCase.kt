package ladyaev.development.myFirstFinance.feature.setupUser.business

import ladyaev.development.myfirstfinance.domain.entities.Country
import ladyaev.development.myfirstfinance.domain.operation.OperationResult

class ChooseCountryUseCase(
    private val featureData: FeatureData
) {
    fun process(country: Country?): OperationResult<Unit, Unit> {
        if (country != null) {
            featureData.country = country
        }
        return OperationResult.Success(Unit)
    }
}