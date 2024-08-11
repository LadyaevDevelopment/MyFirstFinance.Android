package ladyaev.development.myFirstFinance.feature.setupUser.business

import ladyaev.development.myfirstfinance.domain.entities.ResidenceAddress
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.common.SpecifyUserInfoResult

class SpecifyResidenceAddressUseCase(
    private val repository: SetupUserRepository
) {
    suspend fun process(residenceAddress: ResidenceAddress): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        if (residenceAddress.country.id.data.isBlank()) {
            return OperationResult.SpecificFailure(SpecifyUserInfoError.InvalidData)
        }
        if (residenceAddress.city.isBlank()) {
            return OperationResult.SpecificFailure(SpecifyUserInfoError.InvalidData)
        }
        if (residenceAddress.street.isBlank()) {
            return OperationResult.SpecificFailure(SpecifyUserInfoError.InvalidData)
        }
        if (residenceAddress.buildingNumber.isBlank()) {
            return OperationResult.SpecificFailure(SpecifyUserInfoError.InvalidData)
        }
        if (residenceAddress.apartment.isBlank()) {
            return OperationResult.SpecificFailure(SpecifyUserInfoError.InvalidData)
        }
        return repository.specifyResidenceAddress(residenceAddress)
    }
}