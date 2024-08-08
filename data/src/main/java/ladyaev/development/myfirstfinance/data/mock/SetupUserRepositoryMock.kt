package ladyaev.development.myfirstfinance.data.mock

import kotlinx.coroutines.delay
import ladyaev.development.myfirstfinance.domain.operation.OperationResult
import ladyaev.development.myFirstFinance.core.common.misc.PhoneNumber
import ladyaev.development.myFirstFinance.core.common.misc.Seconds
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SetupUserRepository
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SpecifyUserInfoError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.SpecifyUserInfoResult
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeData
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.requireConfirmationCode.RequireConfirmationCodeError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.specifyBirthDate.SpecifyBirthDateError
import ladyaev.development.myfirstfinance.domain.repositories.setupUser.verifyConfirmationCode.VerifyConfirmationCodeError
import java.util.Date
import javax.inject.Inject

class SetupUserRepositoryMock @Inject constructor() : SetupUserRepository {
    override suspend fun requireConfirmationCode(phoneNumber: PhoneNumber): OperationResult<RequireConfirmationCodeData, RequireConfirmationCodeError> {
        delay(500)
        return OperationResult.Success(
            RequireConfirmationCodeData(
                5,
                "0",
                Seconds(10)
            )
        )
    }

    override suspend fun verifyConfirmationCode(
        codeId: String,
        code: String
    ): OperationResult<Unit, VerifyConfirmationCodeError> {
        delay(500)
        return if (code == "11111") {
            OperationResult.SpecificFailure(VerifyConfirmationCodeError.WrongCode)
        } else {
            OperationResult.Success(Unit)
        }
    }

    override suspend fun specifyBirthDate(birthDate: Date): OperationResult<SpecifyUserInfoResult, SpecifyBirthDateError> {
        TODO("Not yet implemented")
    }

    override suspend fun specifyName(
        lastName: String,
        firstName: String,
        middleName: String?
    ): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        TODO("Not yet implemented")
    }

    override suspend fun specifyEmail(email: String): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        TODO("Not yet implemented")
    }

    override suspend fun specifyPinCode(email: String): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        TODO("Not yet implemented")
    }

    override suspend fun specifyResidenceAddress(
        countryIso2Code: String,
        city: String,
        street: String,
        buildingNumber: String,
        apartment: String
    ): OperationResult<SpecifyUserInfoResult, SpecifyUserInfoError> {
        TODO("Not yet implemented")
    }


}