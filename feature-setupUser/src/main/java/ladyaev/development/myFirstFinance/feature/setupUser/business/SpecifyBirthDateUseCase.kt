package ladyaev.development.myFirstFinance.feature.setupUser.business

import ladyaev.development.myFirstFinance.core.common.utils.Age
import ladyaev.development.myFirstFinance.core.common.utils.CurrentDate
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SpecifyUserInfoResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.specifyBirthDate.SpecifyBirthDateError
import java.util.Date
import javax.inject.Inject

class SpecifyBirthDateUseCase @Inject constructor(
    private val currentDate: CurrentDate,
    private val repository: SetupUserRepository
) {
    suspend fun process(birthDate: Date): OperationResult<SpecifyUserInfoResult, SpecifyBirthDateError> {
        if (Age(birthDate, currentDate).years < 18) {
            return OperationResult.SpecificFailure(SpecifyBirthDateError.UserIsMinor)
        }
        return repository.specifyBirthDate(birthDate)
    }
}