package ladyaev.development.myFirstFinance.feature.setupUser.business

import ladyaev.development.myfirstfinance.domain.entities.Country
import ladyaev.development.myfirstfinance.domain.operation.OperationResult

class RequireChosenCountryUseCase(
    private val featureData: FeatureData
) {
    fun process(): OperationResult<Country?, Unit> {
        return OperationResult.Success(featureData.country)
    }
}